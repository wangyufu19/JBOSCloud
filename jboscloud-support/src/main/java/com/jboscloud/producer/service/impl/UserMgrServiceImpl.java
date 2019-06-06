package com.jboscloud.producer.service.impl;
import com.jboscloud.producer.mapper.UserMapper;
import com.jboscloud.producer.service.UserMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMgrServiceImpl implements UserMgrService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map getUserObject(String username) {
        Map params=new HashMap();
        params.put("username",username);
        Map map=userMapper.getUserObject(params);
        System.out.println("******map: "+map);
        return map;
    }
}
