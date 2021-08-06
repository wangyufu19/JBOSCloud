package com.jboscloud.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * TokenStoreConfig
 * @author youfu.wang
 * @date 2021-06-09
 */
@Configuration
public class TokenStoreConfig {
    private static final String SIGNING_KEY = "123456";
    /**
     * 创建令牌存储对象
     */
    @Bean
    public TokenStore tokenStore() {
        /**
         * 使用JwtTokenStore时，必须注入一个JwtAccessTokenConverter，用于解析JWT令牌
         */
        return new JwtTokenStore(jwtAccessTokenConverter());
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
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
