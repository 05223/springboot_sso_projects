package com.hwua.action;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAction {
    @PostMapping("login")
    public String login(String username,String password,String token){
        return "hello world";
    }
}
