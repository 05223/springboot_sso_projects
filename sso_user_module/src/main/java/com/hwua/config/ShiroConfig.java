package com.hwua.config;

import com.hwua.shiro.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() throws Exception{
        //过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //属性注值
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        //登录界面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //成功之后的界面
        shiroFilterFactoryBean.setSuccessUrl("/pages/success.html");
        //不需要认证就可以访问的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/index.html");
        //获取过滤器的集合
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        //设置集合的内容
        /*
        第一个参数是  对应的资源
        第二个参数是  过滤器的名字
         */
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/public/**","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/**","authc");
        return shiroFilterFactoryBean;
    }


    //构建安全管理器
    @Bean("securityManager")
    public DefaultWebSecurityManager getSecurityManager() throws Exception{
        //构建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置自定义规则
        defaultWebSecurityManager.setRealm(getMyRealm());
        return defaultWebSecurityManager;
    }

    //提供自定义的realm类对象
    @Bean("myRealm")
    public MyRealm getMyRealm() throws Exception{
        MyRealm myRealm = new MyRealm();
        //设置加密算法
        myRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
        //myRealm.isCachingEnabled()
        return myRealm;
    }

    //提供自定义的盐值加密的类
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        //构建盐值加密类,指定加密方式为MD5
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
        //加盐次数
        hashedCredentialsMatcher.setHashIterations(2019);
        return hashedCredentialsMatcher;
    }
}
