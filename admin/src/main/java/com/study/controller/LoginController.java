package com.study.controller;

import com.study.common.ResultJson;
import com.study.service.UmsUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private UmsUserService umsUserService;
    @PostMapping("/login")
    public ResultJson login(String username, String password) {
        return ResultJson.success(umsUserService.login(username, password));
    }

    @GetMapping("/logout")
    public ResultJson logout(String userId) {
        System.out.println("调用了退出登录的方法");
        return null;
    }
}
