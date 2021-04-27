package com.jbocloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("support")
public interface UserMgrService {

    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String desc);
}


