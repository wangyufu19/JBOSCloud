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
public class Response {
    public static final String SUCCESS_CODE="0000";
    public static final String FAILURE_CODE="9999";

    private String retCode;
    private String retMsg;


}
