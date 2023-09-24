package com.example.loginproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //增加一个拦截器，检查会话，URL以test开头的都使用此拦截器
        /*registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/auth/**");*/
    }

}
