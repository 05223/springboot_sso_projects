package com.hwua.interceptor;

import com.hwua.domain.User;
import com.hwua.service.JWTServiceImpl;
import com.hwua.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private JWTServiceImpl jwtService;


    //预处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //获取请求携带的数据
        String token = request.getParameter("token");
        //判断token是否存在
        if (token==null||token.trim().equals("")){
            //直接放行登录
            return true;
        }else{
            //判断token的值是否是真的
            String username = jwtService.decodeTokenByKey(token, "username");
            //校验用户是否存在
            User user = loginService.getUserByUsername(username);
            if (user==null){
                //继续登录
                return true;
            }else{
                //不用登陆
            }
        }
        return false;
    }
}
