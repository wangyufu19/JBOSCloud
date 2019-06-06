package com.jboscloud.producer.controller;
import com.jboscloud.producer.common.Return;
import com.jboscloud.producer.service.UserMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserMgrController extends AbstractController{
    @Autowired
    private UserMgrService userMgrService;

    @RequestMapping("/getUserInfo")
    public Return getUserInfo(String username){
        log.info("*******输入参数：username="+username);
        try{
            Map user=userMgrService.getUserObject(username);
            return Return.ok().put("user",user);
        }catch (Exception e){
            return Return.error(e.getMessage());
        }
    }
}
