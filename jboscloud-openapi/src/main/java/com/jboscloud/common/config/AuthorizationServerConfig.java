package com.jboscloud.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * AuthorizationServerConfig
 * @author youfu.wang
 * @date 2021-06-09
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private DataSource dataSource;
    /**
     * 授权服务器安全配置：
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * 获取令牌不需要认证，校验令牌需要认证，允许表单认证
         */
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }
    //jdbc模式的ClientDetailsService 服务配置数据源处理client相关信息的存取
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
    /**
     * 颁发令牌的客户端配置：
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 1:只能使用授权码授权方式和刷新令牌的方式获取令牌
         * 2:只能使用密码授权方式和刷新令牌的方式获取令牌
         * 3:只能使用简化授权方式获取令牌
         * 4:只能使用客户端授权方式获取令牌
         */
        clients.withClientDetails(jdbcClientDetailsService());
    }
    /**
     * 授权服务器端点配置：
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 设置用户认证的管理器和生成的令牌的存储方式
         */
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore);

        /**
         * 必须设置UserDetailsService才能使用refresh_token：指定使用refresh_token换取access_token时，从哪里获取认证用户信息
         */
        endpoints.userDetailsService(userDetailsServiceImpl);
    }
}

