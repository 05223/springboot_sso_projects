package com.hwua.service;

import com.hwua.domain.Role;
import com.hwua.domain.User;
import com.hwua.util.ResponseData;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseData login(User user);

    ResponseData selectUserByUsername(String username);

    //根据用户名称获取用户细节信息
    User selectUserDetailsByUsername(String username);
    //用户修改细节信息
    void updateDetails(User user);

    //级联roles
    User selectUserAndRolesByUsername(String username);

    //更新密码
    void updatePassword(String token,String oldPassword,String newPassword) throws Exception;
}
