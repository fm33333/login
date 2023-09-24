package com.example.loginproject.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginproject.data.dto.LoginDTO;
import com.example.loginproject.data.dto.RegisterDTO;
import com.example.loginproject.data.entity.UserEntity;
import com.example.loginproject.exception.FCException;
import com.example.loginproject.mapper.UserMapper;
import com.example.loginproject.result.R;
import com.example.loginproject.result.ResultCodeEnum;
import com.example.loginproject.service.UserService;
import com.example.loginproject.utils.JWTUtils;
import com.example.loginproject.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     * @param registerDTO
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        UserEntity userEntity = userMapper.selectOne(
                new QueryWrapper<UserEntity>().eq("user_name", registerDTO.getUserName()));
        if (userEntity != null) {
            throw new FCException(ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST);
        }

        // 加密
        String encodePassword = MD5Utils.encrypt(registerDTO.getPassword());

        UserEntity entity = new UserEntity()
                .setUserName(registerDTO.getUserName())
                .setPassword(encodePassword);
        userMapper.insert(entity);
    }

    /**
     * 登录
     * @param loginDTO
     */
    @Override
    public String login(LoginDTO loginDTO) {
        UserEntity userEntity = userMapper.selectOne(
                new QueryWrapper<UserEntity>().eq("user_name", loginDTO.getUserName()));
        if (userEntity == null) {
            throw new FCException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }

        // 密码验证失败
        if (!userEntity.getPassword().equals(MD5Utils.encrypt(loginDTO.getPassword()))) {
            throw new FCException(ResultCodeEnum.USER_CREDENTIALS_ERROR);
        }


        // 密码验证通过，登录成功，使用JWT生成token，并设置到redis
        String token = JWTUtils.createToken(userEntity);
        /*System.out.println("解析token: " + JWTUtils.checkToken(token));
        log.info("token: {}", token);
        log.info("userName: {}", JWTUtils.checkToken(token).get("userName"));
        log.info("password: {}", JWTUtils.checkToken(token).get("password"));*/
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(loginDTO), 1, TimeUnit.DAYS);
        return token;
    }
}
