package com.yue.config;

import com.yue.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String,String> map = new HashMap<>();
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        map.put("/user/verifyCode","anon");
        map.put("/files/**","anon");
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager  defaultWebSecurityManager = new DefaultWebSecurityManager ();
        defaultWebSecurityManager.setRealm(userRealm);

        return defaultWebSecurityManager ;
    }

    @Bean
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();

        //设置用户密码的加密方式
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(credentialsMatcher);

        return userRealm;
    }

}
