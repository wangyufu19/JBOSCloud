package com.jboscloud.producer.controller;
import com.jboscloud.producer.common.Return;
import com.jboscloud.producer.service.UserMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
public class UserMgrController extends AbstractController{
    @Autowired
    private UserMgrService userMgrService;
    @ResponseBody
    @RequestMapping(value="/getUserInfo",method = RequestMethod.GET)
    public Return getUserInfo(@RequestParam Map<String, Object> params){
        log.info("*******输入参数：params="+params);
        try{
            Map user=userMgrService.getUserObject(params.get("username").toString());
            return Return.ok().put("user",user);
        }catch (Exception e){
            return Return.error(e.getMessage());
        }
    }
}
