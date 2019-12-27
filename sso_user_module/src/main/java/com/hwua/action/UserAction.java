package com.hwua.action;

import com.hwua.domain.User;
import com.hwua.mapper.UserMapper;
import com.hwua.service.UserService;
import com.hwua.util.JWTUtil;
import com.hwua.util.RedisUtil;
import com.hwua.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class UserAction {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/user/login")
    public ResponseData login(@RequestBody User user){
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("登录...");
        //构建返回信息的对象(允许Spring管理)
        ResponseData responseObj = new ResponseData();
        //构建UsernamePasswordToken对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            //执行登录
            subject.login(usernamePasswordToken);
            //判断是否登录成功
            if (subject.isAuthenticated()){
                //设置成功的返回值
                String token = JWTUtil.encodeToken(user);
                responseObj.setCode(0);
                responseObj.setMessage(token);
                System.out.println("登录成功!");

                //在redis中设置token的值
                redisUtil.setToken(token,user.getPassword());
                log.info(token);
                return responseObj;
            }
        } catch ( UnknownAccountException uae ) {
            System.out.println(uae.getMessage());
        } catch ( IncorrectCredentialsException ice ) {
            System.out.println(ice.getMessage());
        } catch (Exception e) {}
         //设置失败的返回值
        responseObj.setMessage("login error!!!");
        return responseObj;
    }

    @Autowired
    public UserMapper userMapper;


    @RequestMapping("/user/addUser")
    public String addUser(String username,String password){
        System.out.println("addUser");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userMapper.insert(user);
        return "success";
    }
//    @RequiresRoles(value = "超级管理员")
    @PostMapping("/user/selectUserDetailsByUsername")
    public User selectUserDetailsByUsername(String token) throws Exception{
        String username = JWTUtil.decodeToken(token, "username");
        User user = userService.selectUserDetailsByUsername(username);
        System.out.println(user);
        return user;
    }

    @PostMapping("/user/updateDetails")
    public String updateDetails(@RequestBody User user){
        userService.updateDetails(user);
        return "信息修改成功!";
    }

    @PostMapping("/user/updatePassword")
    public String updatePassword(ServletRequest request, String oldPassword, String newPassword){
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader("refresh_token");
            userService.updatePassword(token,oldPassword,newPassword);
            return "信息修改成功!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败!";
    }

    //从缓存获取token
    @PostMapping("user/token")
    public ResponseData getToken(){
        return null;
    }
}
