package com.jboscloud.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jboscloud.common.utils.JacksonUtils;
import com.jboscloud.common.utils.StringUtils;
import com.jboscloud.openapi.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSecurityConfig
 * @author youfu.wang
 * @date 2021-05-21
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true ,jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 用户认证配置
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 获取认证用户信息
         */
        auth.userDetailsService(userDetailsService);
    }

    /**
     * HTTP安全配置
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  //禁用csrf
                .sessionManagement().disable()  //禁用session
                .formLogin().disable() //禁用form登录
                .addFilterAfter(loginFilter(),UsernamePasswordAuthenticationFilter.class)
                .logout()
                .invalidateHttpSession(true)
                .addLogoutHandler(new WebLogoutHandler()).logoutSuccessHandler(new WebLogoutSuccessHandler())
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint(){
                    //未通过认证请求，返回异常信息
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                        ResponseBody responseBody=ResponseBody.error("对不起，非法请求");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(JacksonUtils.toJson(responseBody));
                        out.flush();
                        out.close();
                    }
                })
                //允许跨域
                .and().cors();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 自定义 UsernamePasswordAuthenticationFilter 过滤器
     */
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 登录请求地址
        loginFilter.setFilterProcessesUrl("/sys/auth/login");
        // 返回登录成功后数据
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                ResponseBody responseBody=ResponseBody.ok("登录成功！");
                Map<String,Object> data=new HashMap<String,Object>();
                data.put("principal", authentication.getPrincipal());
                response.setStatus(HttpServletResponse.SC_OK);
                responseBody.setData(data);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(responseBody));
                out.flush();
                out.close();
            }
        });
        // 返回登录失败后数据
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                ResponseBody responseBody=ResponseBody.error("登录失败！");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(responseBody));
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }
    public class LoginFilter extends UsernamePasswordAuthenticationFilter {
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            // 判断请求是否是json格式，如果不是直接调用默认表单认证方式
            if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
                // 把request的json数据转换为Map
                Map<String, String> loginData = new HashMap<>();
                try {
                    loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 调用父类的getParameter() 方法获取key值
                String username = StringUtils.replaceNull(loginData.get(this.getUsernameParameter()));
                String password = StringUtils.replaceNull(loginData.get(this.getPasswordParameter()));
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username.trim(), password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else {
                return super.attemptAuthentication(request, response);
            }
        }
    }
    public class WebLogoutHandler implements LogoutHandler {

        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if(authentication!=null){
                User user = (User) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
        }
    }
    public class WebLogoutSuccessHandler implements LogoutSuccessHandler{

        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = response.getWriter();
            ResponseBody responseBody=ResponseBody.ok("退出成功！");
            out.write(JacksonUtils.toJson(responseBody));
            out.flush();
            out.close();
        }
    }
}
