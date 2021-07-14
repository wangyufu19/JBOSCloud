package com.jboscloud.openapi.request;

import com.jboscloud.application.service.ResourceAuthService;
import com.jboscloud.common.spring.SpringContextHolder;
import com.jboscloud.common.utils.JacksonUtils;
import com.jboscloud.openapi.response.ResponseBody;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TokenAuthProcessor
 * @author youfu.wang
 * @date 2021-05-05
 */
public class UriAuthRequest extends OpenApiRequest {
    private static final Logger logger= LoggerFactory.getLogger(UriAuthRequest.class);
    private RequestContext ctx;
    private ApplicationContext applicationContext= SpringContextHolder.getApplicationContext();

    public UriAuthRequest(RequestContext ctx){
        this.ctx=ctx;
    }

    public Object doRequest (Authentication authentication) {
        Object url=ctx.getRouteHost();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json;charset=utf-8");
        logger.info("send {} request to {}", request.getMethod(),request.getRequestURL().toString());
        String clientId=authentication.getPrincipal().toString();
        String resourceId=request.getRequestURI();
        try{
            //验证请求的token有效性
            this.validateToken(request);
            //验证请求资源的客户端授权
            ResourceAuthService resourceAuthService=applicationContext.getBean(ResourceAuthService.class);
            if(!resourceAuthService.validateResourceAuth(clientId,resourceId)){
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(JacksonUtils.toJson(ResponseBody.error("访问资源["+resourceId+"]未授权")));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(JacksonUtils.toJson(ResponseBody.error("访问资源失败["+resourceId+"]")));
        }
        return null;
    }
    private void validateToken(HttpServletRequest request){
        Object accessToken = request.getParameter("access_token");
        if(accessToken == null) {
            accessToken=request.getHeader("access_token");
        }
        if(accessToken == null) {
            logger.error("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(JacksonUtils.toJson(ResponseBody.error("access token is empty")));
        }
    }
}
