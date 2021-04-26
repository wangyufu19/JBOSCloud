package com.jbocloud.consumer.controller;

import com.jbocloud.consumer.service.UserMgrService;
import com.jboscloud.common.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserMgrController {
    @Autowired
    private UserMgrService userMgrService;

    @RequestMapping("/getUserInfo")
    public Return getUserInfo(){
        return userMgrService.getUserInfo();
    }
}
