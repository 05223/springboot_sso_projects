package com.hwua.service.impl;

import com.hwua.domain.Role;
import com.hwua.domain.User;
import com.hwua.mapper.UserMapper;
import com.hwua.service.UserService;
import com.hwua.util.JWTUtil;
import com.hwua.util.PasswordUtil;
import com.hwua.util.ResponseData;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseData login(User srcUser) {
        ResponseData responseData = new ResponseData();

        User user = userMapper.selectByUsername(srcUser);
        //判断用户名是否存在
        if (user==null){
            responseData.setCode(503);
            responseData.setMessage("用户名不存在");
            responseData.setT(srcUser);
            return responseData;
        }
        //判断密码是否正确
        if (user!=null){
            boolean md5 = PasswordUtil.checkPassword(srcUser.getPassword(), user.getPassword(), "md5", 2019);
            if (md5) {
                responseData.setMessage("登录成功!");
                responseData.setT(user);
                return responseData;
            } else {
                responseData.setCode(503);
                responseData.setMessage("密码错误!");
                responseData.setT(srcUser);
                return responseData;
            }
        }
        return null;
    }

    //根据用户名查询用户
    @Override
    public ResponseData selectUserByUsername(String username) {
        ResponseData responseData = new ResponseData<User>();
        User user = userMapper.selectUserByUsername(username);
        responseData.setT(user);
        return responseData;
    }

    //查询用户个人信息
    @Override
    public User selectUserDetailsByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        return user;
    }

    //用户更新细节信息
    @Override
    public void updateDetails(User user) {
        String password = user.getPassword();
        String username = user.getUsername();
        Md5Hash hash = new Md5Hash(password, username, 2019);
        user.setPassword(hash.toString());
        userMapper.updateDetails(user);
    }


    @Override
    public User selectUserAndRolesByUsername(String username) {
        User user = userMapper.selectRolesByUsername(username);
        System.out.println(user);
        return user;
    }

    @Override
    public void updatePassword(String token, String oldPassword, String newPassword) throws Exception{
        String username = JWTUtil.decodeToken(token, "username");
        User selectUserByUsername = userMapper.selectUserByUsername(username);
        String password = selectUserByUsername.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(oldPassword);
        boolean md5 = PasswordUtil.checkPassword(oldPassword, password, "md5", 2019);
        if (md5) {
            //密码加密
            Md5Hash md5Hash = new Md5Hash(oldPassword, username, 2019);
            userMapper.updatePassword(username,md5Hash.toString());
            System.out.println("更改成功!");
        }
    }


}
