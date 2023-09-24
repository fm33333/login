package com.example.loginproject.controller;

import com.example.loginproject.data.dto.LoginDTO;
import com.example.loginproject.data.dto.RegisterDTO;
import com.example.loginproject.result.Empty;
import com.example.loginproject.result.R;
import com.example.loginproject.result.ResultCodeEnum;
import com.example.loginproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param registerDTO
     * @return
     */
    @PostMapping("/register")
    public R<Empty> register(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
            return R.ok();
        } catch (Exception e) {
            return R.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
        }
    }

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            return R.ok(userService.login(loginDTO));
        } catch (Exception e) {
            return R.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
        }
    }


    /*@PostMapping("/logout")
    public R<Empty> logout(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        System.out.println(request.getHeader("Authorization"));
        return R.ok();
    }*/

}
