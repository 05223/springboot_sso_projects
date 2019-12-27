package com.hwua.config;

import com.hwua.domain.Permission;
import com.hwua.domain.Role;
import com.hwua.domain.User;
import com.hwua.service.UserService;
import com.hwua.util.PasswordUtil;
import com.hwua.util.ResponseData;
import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户名
        String username = principals.getPrimaryPrincipal().toString();
        //根据用户名查询用户
        User user = userService.selectUserAndRolesByUsername(username);
        System.out.println("授权...");
        //构建对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //给用户关联角色
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()){
            Role role = iterator.next();
            //设置到shiro的对象 SimpleAuthorizationInfo 里面
            simpleAuthorizationInfo.addRole(role.getName());

            //给角色关联权限
            Set<Permission> permissions = role.getPermissions();
            Iterator<Permission> iterator1 = permissions.iterator();
            while (iterator1.hasNext()){
                Permission permission = iterator1.next();
                //设置到shiro的对象 SimpleAuthorizationInfo 里面
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo...");
        //把参数强转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户名
        String username = usernamePasswordToken.getUsername();

        //根据用户名查询
        ResponseData responseData = null;
        try{
            responseData = userService.selectUserByUsername(username);
            User user = (User) responseData.getT();
            //判断对象是否为空
            if (user!=null){
                //盐
                ByteSource bytes = ByteSource.Util.bytes(username);
                //构建对象
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(usernamePasswordToken,user.getPassword(),bytes,getName());
                return simpleAuthenticationInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
