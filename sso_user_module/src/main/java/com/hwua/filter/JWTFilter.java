package com.hwua.filter;

import com.hwua.domain.User;
import com.hwua.mapper.UserMapper;
import com.hwua.service.UserService;
import com.hwua.util.JWTUtil;
import com.hwua.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component(value = "jWTFilter")
public class JWTFilter extends BasicHttpAuthenticationFilter {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        log.info("--------------------isAccessAllowed-------------------------");
//        if (isLoginAttempt(request, response)){
//            try {
//                executeLogin(request,response);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        log.info("--------------------isLoginAttempt-------------------------");
//        HttpServletRequest req = (HttpServletRequest) request;
//        //优化返回方案
//        //判断请求头是否携带token
//        String token = req.getHeader("token");
//        if (token==null||token.trim().equals("")){
//            token = req.getParameter("token");
//        }
//        return token!=null;
//    }
//
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        log.info("--------------------executeLogin-------------------------");
//        HttpServletRequest req = (HttpServletRequest) request;
//        //获取请求消息头信息或者路径信息    获取token
//        String token = req.getHeader("token");
//        if (token==null||token.trim()==""){
//            token = req.getParameter("token");
//        }
//        //上述代码确保了token一定不为空
//        /**
//         * login 方法的参数是AuthenticationToken 对象
//         * 两个办法:
//         * 1)构建UsernamPasswordToken
//         * 2)自定义一个token对象
//         */
//        MyJwtToken myJwtToken = new MyJwtToken();
//        myJwtToken.setToken(request.getParameter("username"));
//        myJwtToken.setPassword(request.getParameter("password"));
//        getSubject(request, response).login(myJwtToken);
//        return true;
//    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //判断token的值
        String token = req.getHeader("token");
        if (token==null||token.trim()==""){
            token = req.getParameter("token");
            if (token==null||token.trim()==""){
                return true;
            }
        }
        //从缓存中获取token,验证token的值
        String tokenValue = redisUtil.getToken(token);
        if (tokenValue==null) {
            log.info("redis缓存无此token");
            return true;
        } else {
            log.info("---------拦截器,redis缓存有此token,直接登陆!----------");
            return true;
        }



        //判断token的值是否是真的
//        String username = JWTUtil.decodeToken(token, "username");
//        User paramUser = new User();
//        paramUser.setUsername(username);
//        //校验用户是否存在
//        User user = userMapper.selectByUsername(paramUser);
//        if (user==null){
//            //继续登录
//            return true;
//        }else{
//            //不用登陆
//            System.out.println("token直接登录");
//            return true;
//        }



    }
}
