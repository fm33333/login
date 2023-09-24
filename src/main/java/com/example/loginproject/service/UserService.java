package com.example.loginproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginproject.data.dto.LoginDTO;
import com.example.loginproject.data.dto.RegisterDTO;
import com.example.loginproject.data.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<UserEntity> {


    void register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);
}
