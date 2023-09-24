package com.example.loginproject.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.loginproject.data.entity.UserEntity;
import com.example.loginproject.service.UserService;
import com.example.loginproject.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        log.info("preHandle ======================= token: {}", token);
        /*System.out.println(request.getRequestURI());
        System.out.println(request.getSession());
        System.out.println(request.getRemoteUser());*/
        if (token == null) {
            return false;
        }
        String userName = (String) JWTUtils.getMapByToken(token).get("userName");
        log.info("userName: {}", userName);
        UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().eq("user_name", userName));
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    //执行控制类方法后调用
    @Override
    public void postHandle(
            HttpServletRequest req, HttpServletResponse resp, Object obj, ModelAndView mav
    )  throws Exception {
        Long endTs = System.currentTimeMillis();
        Long startTs = (Long) req.getAttribute("ts");
        Long tS = endTs - startTs;
        String traceId = (String) req.getAttribute("traceId");

        System.out.println("[" + traceId + "]" + "cost time:" + tS);
    }

}
