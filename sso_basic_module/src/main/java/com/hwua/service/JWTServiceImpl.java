package com.hwua.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hwua.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTServiceImpl {

    //构建一个token
    public String encodeToken(User user) throws Exception {
        //获取JWTCreator中的Builder对象
        JWTCreator.Builder builder = JWT.create().withClaim("username", user.getUsername()).withClaim("loginDate", new Date());
        //构建一个加密的凭证
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
        //构建token
        String token = builder.sign(algorithm);
        return token;
    }

    //解码token
    public String decodeTokenByKey(String token, String key) throws Exception {
        //构建解码器
        DecodedJWT decode = JWT.decode(token);
        //获取有效负载里面的数据
        String stringClaim = decode.getClaim(key).asString();
        return stringClaim;
    }
}
