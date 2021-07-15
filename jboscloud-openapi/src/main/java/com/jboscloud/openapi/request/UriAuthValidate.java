package com.jboscloud.openapi.request;

import com.jboscloud.application.service.ResourceAuthService;
import com.jboscloud.common.spring.SpringContextHolder;
import com.jboscloud.openapi.response.ResponseBody;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * UriRequestValidate
 * @author youfu.wang
 * @date 2021-05-05
 */
public class UriAuthValidate implements RequestValidate{
    private HttpServletRequest request;
    private Authentication authentication;
    private ApplicationContext applicationContext= SpringContextHolder.getApplicationContext();

    public UriAuthValidate(HttpServletRequest request,Authentication authentication){
        this.request=request;
        this.authentication=authentication;
    }
    public ResponseBody validate() {
        String clientId=authentication.getPrincipal().toString();
        String resourceId=request.getRequestURI();
        ResourceAuthService resourceAuthService=applicationContext.getBean(ResourceAuthService.class);
        if(!resourceAuthService.validateResourceAuth(clientId,resourceId)){
            return ResponseBody.error("访问资源["+resourceId+"]未授权");
        }
        return ResponseBody.ok();
    }
}
