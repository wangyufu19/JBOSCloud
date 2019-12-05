package com.jboscloud.producer.service.impl;
import com.jboscloud.producer.mapper.UserMapper;
import com.jboscloud.producer.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map auth(String username, String password) {
        Map params=new HashMap();
        params.put("username",username);
        Map map=userMapper.auth(params);
        System.out.println("******map: "+map);
        return map;
    }
}
