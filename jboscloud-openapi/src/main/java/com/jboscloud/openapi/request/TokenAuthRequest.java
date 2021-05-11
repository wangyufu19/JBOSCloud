package com.jboscloud.openapi.request;

import com.jboscloud.openapi.response.HttpAccessResponse;
import com.jboscloud.openapi.response.Return;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TokenAuthProcessor
 * @author youfu.wang
 * @date 2021-05-05
 */
public class TokenAuthRequest extends OpenApiRequest {
    private static final Logger log= LoggerFactory.getLogger(TokenAuthRequest.class);
    private  RequestContext ctx;

    public TokenAuthRequest(RequestContext ctx){
        this.ctx=ctx;
    }

    public Object doRequest () {
        try{
            HttpServletRequest request = ctx.getRequest();
            HttpServletResponse response = ctx.getResponse();
            log.info("send {} request to {}", request.getMethod(),request.getRequestURL().toString());
            Object accessToken = request.getParameter("accessToken");
            if(accessToken == null) {
                log.warn("access token is empty");
                ctx.setSendZuulResponse(false);
                HttpAccessResponse httpAccessResponse=new HttpAccessResponse(request,response);
                httpAccessResponse.doResponseBody(Return.error("access token is empty"));
            }
        }catch (Exception e){
            log.error("Gateway Filter Exception");
            ctx.set("error.retCode",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.retMsg",e);
        }
        return null;
    }
}
