package com.jboscloud.openapi.filter;
import com.jboscloud.openapi.request.OpenApiRequest;
import com.jboscloud.openapi.request.ApiUriRequest;
import com.jboscloud.openapi.request.TokenAuthValidate;
import com.jboscloud.openapi.request.UriAuthValidate;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
/**
 * OpenApiPreFilter
 * @author youfu.wang
 * @date 2021-05-05
 */
@Component
public class OpenApiPreFilter extends ZuulFilter{
    private static final Logger log= LoggerFactory.getLogger(OpenApiPreFilter.class);
    @Override
    public String filterType() {
        return  FilterConstants.PRE_TYPE;
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
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        try{
            ApiUriRequest apiUriRequest=new ApiUriRequest(ctx);
            apiUriRequest.addRequestValidate(new TokenAuthValidate(ctx.getRequest()));
            apiUriRequest.addRequestValidate(new UriAuthValidate(ctx.getRequest(),authentication));
            return apiUriRequest.doRequest();
        }catch (Exception e){
            log.error("Gateway Filter Exception");
            ctx.set("error.status",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.message",e);
        }
        return null;
    }

}
