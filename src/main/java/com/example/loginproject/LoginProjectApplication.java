package com.example.loginproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.loginproject.mapper")
public class LoginProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginProjectApplication.class, args);
    }

}
