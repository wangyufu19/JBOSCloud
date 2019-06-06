package com.jboscloud.producer.controller;

import com.jboscloud.producer.common.Return;
import com.jboscloud.producer.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserAuthController extends AbstractController{
    @Autowired
    private UserAuthService userAuthService;
    @RequestMapping("/auth")
    public Return auth(@RequestParam String username, @RequestParam String password){
        log.info("*******输入参数：username="+username+";password="+password);
        Map map=null;
        try{
            map=userAuthService.auth(username,password);
            if(map==null){
                return  Return.error("账号或密码不正确");
            }
        }catch (Exception e){
            return  Return.error("账号或密码不正确");
        }
        return Return.ok();
    }
}
