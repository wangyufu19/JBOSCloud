package com.jboscloud.openapi.filter;
import com.jboscloud.openapi.response.OpenApiResponse;
import com.jboscloud.openapi.response.Return;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * OpenApiPreFilter
 * @author youfu.wang
 * @date 2021-05-05
 */
@Component
public class OpenApiPostFilter extends ZuulFilter{
    private static final Logger log= LoggerFactory.getLogger(OpenApiPostFilter.class);
    @Override
    public String filterType() {
        return  FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        try{
            if(HttpServletResponse.SC_NOT_FOUND==ctx.getResponseStatusCode()){
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpServletResponse.SC_NOT_FOUND);
                OpenApiResponse openApiResponse=new OpenApiResponse();
                Return ret=Return.error(String.valueOf(HttpServletResponse.SC_NOT_FOUND),ctx.getRequest().getRequestURI()+" is not found");
                ctx.setResponseBody(openApiResponse.getBody(ret));
                return null;
            }
        }catch (Exception e){
            ctx.set("error.status_code",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.message",e);
        }
        return null;
    }

}
