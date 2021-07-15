package com.jboscloud.openapi.request;

import com.jboscloud.openapi.response.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

/**
 * TokenAuthValidate
 * @author youfu.wang
 * @date 2021-05-05
 */
public class TokenAuthValidate implements RequestValidate{
    private static final Logger logger= LoggerFactory.getLogger(TokenAuthValidate.class);
    private HttpServletRequest request;

    public TokenAuthValidate(HttpServletRequest request){
        this.request=request;
    }
    public ResponseBody validate() {
        Object accessToken = request.getParameter("access_token");
        if(accessToken == null) {
            accessToken=request.getHeader("access_token");
        }
        if(accessToken == null) {
            logger.error("access token is empty");
            return ResponseBody.error("access token is empty");
        }
        return ResponseBody.ok();
    }
}
