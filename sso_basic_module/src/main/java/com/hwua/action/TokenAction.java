package com.hwua.action;

import com.hwua.domain.User;
import com.hwua.service.JWTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenAction  {
    @Autowired
    private JWTServiceImpl jwtService;

    @GetMapping("encodeToken")
    public String encodeToken(User user) throws Exception{
        String token = jwtService.encodeToken(user);
        return token;
    }

    @GetMapping("decodeToken")
    public String decodeToken(String token) throws Exception{
        String decodeToken = jwtService.decodeToken(token);
        return decodeToken;
    }
}
