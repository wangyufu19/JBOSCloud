package com.jbocloud.consumer.controller;

import com.jbocloud.consumer.service.UserMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserMgrController {
    @Autowired
    private UserMgrService userMgrService;

    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(){
        return userMgrService.getUserInfo("admin","123456","超级管理员");
    }
}
