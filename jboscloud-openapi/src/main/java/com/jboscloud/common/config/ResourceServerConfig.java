package com.jboscloud.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jboscloud.openapi.response.Response;
import com.jboscloud.openapi.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint(){
                    //未通过认证请求，返回异常信息
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
                        ResponseBody responseBody= ResponseBody.error(403,"对不起，非法请求");
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(responseBody));
                        out.flush();
                        out.close();
                    }
                });
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
        /**
         * 设置自定义的异常响应
         */
        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionTranslator(new ResourceServerExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }
    @Service
    public class ResourceServerExceptionTranslator implements WebResponseExceptionTranslator {
        public ResponseEntity<Response> translate(Exception e) {
            if (e instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(ResponseBody.error(oAuth2Exception.getHttpErrorCode(),oAuth2Exception.getMessage()));
            }else if(e instanceof AuthenticationException){
                AuthenticationException authenticationException = (AuthenticationException) e;
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseBody.error(401,authenticationException.getMessage()));
            }else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ResponseBody.ok(200,e.getMessage()));
            }
        }
    }

    @JsonSerialize(using = ResourceServerExceptionSerializer.class)
    public class ResourceServerException extends OAuth2Exception {
        private String oAuth2ErrorCode;

        private int httpErrorCode;

        public ResourceServerException(String msg, String oAuth2ErrorCode, int httpErrorCode) {
            super(msg);
            this.oAuth2ErrorCode = oAuth2ErrorCode;
            this.httpErrorCode = httpErrorCode;
        }

        public ResourceServerException(String msg) {
            super(msg);
        }

        public String getOAuth2ErrorCode() {
            return oAuth2ErrorCode;
        }

        public int getHttpErrorCode() {
            return httpErrorCode;
        }
    }
    public class  ResourceServerExceptionSerializer extends StdSerializer<ResourceServerException>  {
        public ResourceServerExceptionSerializer() {
            super(ResourceServerException.class);
        }
        public void serialize(ResourceServerException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("retCode",-1);
            jsonGenerator.writeObjectField("retMsg","登陆凭证过期");
            jsonGenerator.writeEndObject();
        }

    }
}
