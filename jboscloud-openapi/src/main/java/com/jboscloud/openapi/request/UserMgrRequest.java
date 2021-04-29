package com.jboscloud.openapi.request;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * UserMgrRequest
 * @author youfu.wang
 * @date 2021-04-29
 */
public interface UserMgrRequest {
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String desc);
}
