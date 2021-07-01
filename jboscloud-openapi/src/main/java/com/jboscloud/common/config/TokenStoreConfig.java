package com.jboscloud.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * TokenStoreConfig
 * @author youfu.wang
 * @date 2021-06-09
 */
@Configuration
public class TokenStoreConfig {
    public String TOKEN_STORE_JDBC="jdbc";

    @Value("${spring.security.oauth2.token-store}")
    private String tokenStore;

    @Autowired
    private DataSource dataSource;
    /**
     * 创建令牌存储对象
     */
    @Bean
    public TokenStore tokenStore() {
        if(TOKEN_STORE_JDBC.equals(this.tokenStore)){
            /**
             * 给jdbc模式的TokenStore配置数据源处理token的存取
             */
            return new JdbcTokenStore(dataSource);
        }else {
            /**
             * 使用JwtTokenStore时，必须注入一个JwtAccessTokenConverter，用于解析JWT令牌
             */
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
    }

    /**
     * 创建JWT令牌转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        /**
         * 设置JWT令牌的签名key
         */
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("signingKey");
        return converter;
    }
}
