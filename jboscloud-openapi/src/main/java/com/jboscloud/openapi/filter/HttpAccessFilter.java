package com.jboscloud.openapi.filter;

import com.jboscloud.common.Return;
import com.jboscloud.openapi.response.HttpAccessResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * HttpAccessFilter
 * @author youfu.wang
 * @date 2019-06-05
 */
public class HttpAccessFilter extends ZuulFilter{
    private static final Logger log= LoggerFactory.getLogger(HttpAccessFilter.class);
    public static final String APPLICATION_TEXT_HTML="text/html";
    public static final String APPLICATION_XML="application/xml";
    public static final String APPLICATION_JSON="application/json ";
    public static final String APPLICATION_IMAGE_GIF="image/gif";
    public static final String APPLICATION_IMAGE_JPEG="image/jpeg";
    public static final String APPLICATION_IMAGE_PNG="image/png";
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        try{
            HttpServletRequest request = ctx.getRequest();
            HttpServletResponse response = ctx.getResponse();
            log.info("send {} request to {}", request.getMethod(),request.getRequestURL().toString());
//            Object accessToken = request.getParameter("accessToken");
//            if(accessToken == null) {
//                log.warn("access token is empty");
//                ctx.setSendZuulResponse(false);
//                HttpAccessResponse httpAccessResponse=new HttpAccessResponse(request,response);
//                httpAccessResponse.doResponseBody(Return.error());
//                return null;
//            }
        }catch (Exception e){
            log.error("Gateway Filter Exception");
            ctx.set("error.retCode",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.retMsg",e);
        }
        return null;
    }
}
