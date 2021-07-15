package com.jboscloud.openapi.request;

import org.springframework.security.core.Authentication;

/**
 * OpenApiProcessor
 * @author youfu.wang
 * @date 2021-05-05
 */
public abstract class OpenApiRequest {

    public abstract Object doRequest();
}
