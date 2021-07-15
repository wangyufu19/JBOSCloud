package com.jboscloud.openapi.request;

import com.jboscloud.common.spring.SpringContextHolder;
import com.jboscloud.common.utils.JacksonUtils;
import com.jboscloud.openapi.response.ResponseBody;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiUriRequest
 * @author youfu.wang
 * @date 2021-05-05
 */
public class ApiUriRequest extends OpenApiRequest {
    private static final Logger logger= LoggerFactory.getLogger(ApiUriRequest.class);
    private RequestContext ctx;
    private ApplicationContext applicationContext= SpringContextHolder.getApplicationContext();
    private List<RequestValidate> requestValidateList=new ArrayList<RequestValidate> ();

    public ApiUriRequest(RequestContext ctx){
        this.ctx=ctx;
    }

    public void addRequestValidate(RequestValidate requestValidate){
        requestValidateList.add(requestValidate);
    }
    public Object doRequest () {
        HttpServletRequest request = ctx.getRequest();
        String resourceId=request.getRequestURI();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json;charset=utf-8");
        logger.info("send {} request to {}", request.getMethod(),request.getRequestURL().toString());
        try{
            for(RequestValidate requestValidate:requestValidateList){
                ResponseBody responseBody=requestValidate.validate();
                if(!ResponseBody.SUCCESS_CODE.equals(responseBody.getRetCode())){
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(JacksonUtils.toJson(responseBody));
                    break;
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(JacksonUtils.toJson(ResponseBody.error("访问资源失败["+resourceId+"]")));
        }
        return null;
    }
}
