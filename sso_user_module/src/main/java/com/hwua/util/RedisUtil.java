package com.hwua.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于String的redis工具类
 */
@Component
public class RedisUtil {
    //注入redisTemplate
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //把token存入redis
    //有效时间为30min
    public void setToken(String token,String password){
        redisTemplate.opsForValue().set(token,password,30, TimeUnit.MINUTES);
    }

    //获取token
    public String getToken(String token){
        String password = redisTemplate.opsForValue().get(token);
        return password;
    }
}
