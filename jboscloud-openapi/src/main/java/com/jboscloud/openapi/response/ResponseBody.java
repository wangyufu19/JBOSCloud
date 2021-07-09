package com.jboscloud.openapi.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Response
 * @author youfu.wang
 * @date 2021-07-08
 */
@Data
@Setter
@Getter
public class ResponseBody extends Response{
    private Object data;
    public static ResponseBody ok(){
        ResponseBody responseBody=new ResponseBody();
        responseBody.setRetCode(SUCCESS_CODE);
        responseBody.setRetMsg(SUCCESS_MESSAGE);
        return responseBody;
    }
    public static ResponseBody ok(String retMsg){
        ResponseBody responseBody=new ResponseBody();
        responseBody.setRetCode(SUCCESS_CODE);
        responseBody.setRetMsg(retMsg);
        return responseBody;
    }
    public static ResponseBody ok(int retCode,String retMsg){
        ResponseBody responseBody=new ResponseBody();
        responseBody.setRetCode(String.valueOf(retCode));
        responseBody.setRetMsg(retMsg);
        return responseBody;
    }

    public static ResponseBody error(int errorCode,String errorMsg){
        ResponseBody responseBody=new ResponseBody();
        responseBody.setRetCode(String.valueOf(errorCode));
        responseBody.setRetMsg(errorMsg);
        return responseBody;
    }
    public static ResponseBody error(String errorMsg){
        ResponseBody responseBody=new ResponseBody();
        responseBody.setRetCode(FAILURE_CODE);
        responseBody.setRetMsg(errorMsg);
        return responseBody;
    }
}
