package com.jboscloud.openapi.filter;
import com.jboscloud.openapi.filter.processor.OpenApiProcessor;
import com.jboscloud.openapi.filter.processor.TokenAuthProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
/**
 * HttpAccessFilter
 * @author youfu.wang
 * @date 2021-05-05
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
            OpenApiProcessor openApiProcessor=new TokenAuthProcessor(ctx);
            openApiProcessor.doProcess();
        }catch (Exception e){
            log.error("Gateway Filter Exception");
            ctx.set("error.retCode",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.retMsg",e);
        }
        return null;
    }
}
