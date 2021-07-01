package com.jboscloud.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
/**
 * ResourceServerConfig
 * @author youfu.wang
 * @date 2021-06-09
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/sys/auth/login").permitAll()
                .anyRequest().authenticated();
    }
    /**
     * 资源服务器的安全配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /**
         * 设置令牌的存储方式
         */
        resources.tokenStore(tokenStore);
    }


}
