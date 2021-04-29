package com.jbocloud.consumer.service;

import com.jbocloud.consumer.service.fallback.UserMgrServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * UserMgrService
 * @author youfu.wang
 * @date 2021-04-29
 */
@FeignClient(name = "support" ,fallback = UserMgrServiceFallback.class)
public interface UserMgrService {
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String desc);

}


