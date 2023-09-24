package com.example.loginproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.loginproject.data.entity.User;
import com.example.loginproject.data.entity.UserEntity;
import com.example.loginproject.result.Empty;
import com.example.loginproject.result.R;
import com.example.loginproject.service.UserService;
import com.example.loginproject.utils.MD5Utils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 测试拦截器
     *
     * @return
     */
    /*@GetMapping("")
    public R<Empty> test() {
        return R.ok();
    }*/

    /**
     * 根据用户名和密码从MySQL查询用户基本信息
     *
     * @param userEntity
     * @return
     */
    @PostMapping("/getFromMysql")
    public R<UserEntity> getFromMysql(@RequestBody UserEntity userEntity) {
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>()
                .eq("user_name", userEntity.getUserName())
                .eq("password", MD5Utils.encrypt(userEntity.getPassword())));
        redisTemplate.opsForValue().set(userEntity.getUserName(), user);
        log.info("user: {}", user);
        return R.ok(user);
    }


    /**
     * @param userEntity
     * @return
     */
    @PostMapping("/getFromRedis")
    public R<UserEntity> getFromRedis(@RequestBody UserEntity userEntity) {
        UserEntity user = (UserEntity) redisTemplate.opsForValue().get(userEntity.getUserName());
        log.info("user: {}", user.toString());
        return R.ok(user);
    }

    /**
     * 保存信息到MongoDB数据库
     *
     * @param user
     * @return
     */
    @PostMapping("/saveToMongoDB")
    public R<Empty> saveToMongoDB(@RequestBody User user) {
        log.info("user: {}", user.toString());
        mongoTemplate.insert(user);
        return R.ok();
    }

    /**
     * 获取所有user
     *
     * @return
     */
    @GetMapping("/getAllFromMongoDB")
    public R<List<User>> getAllFromMongoDB() {
        return R.ok(mongoTemplate.findAll(User.class));
    }

    /**
     * 根据条件进行查询
     * @param user
     * @return
     */
    @PostMapping("/getFromMongoDB")
    public R<List<User>> getFromMongoDB(@RequestBody User user) {
        log.info("user: {}", user.toString());
        Query query = new Query(
                Criteria.where("userName").is(user.getUserName()));
        return R.ok(mongoTemplate.find(query, User.class));
    }

    /**
     * 更新MongoDB的数据
     * @param user
     * @return
     */
    @PostMapping("/updateFromMongoDB")
    public R<List<User>> updateFromMongoDB(@RequestBody User user) {
        log.info("user: {}", user.toString());
        Query query = new Query(
                Criteria.where("_id").is(user.getId())
        );
        Update update = new Update()
                .set("userName", user.getUserName())
                .set("telephone", user.getTelephone());
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        log.info("result: {}", result);
        return R.ok(mongoTemplate.find(query, User.class));
    }

    /**
     * 删除MongoDB的数据
     * @param user
     * @return
     */
    @PostMapping("/deleteFromMongoDB")
    public R<Empty> deleteFromMongoDB(@RequestBody User user) {
        log.info("user: {}", user.toString());
        Query query = new Query(
                Criteria.where("_id").is(user.getId())
        );
        DeleteResult result = mongoTemplate.remove(query, User.class);
        log.info("result: {}", result);
        return R.ok();
    }

}
