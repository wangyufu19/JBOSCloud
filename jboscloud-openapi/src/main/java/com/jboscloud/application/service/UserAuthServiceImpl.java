package com.jboscloud.application.service;

import com.jboscloud.application.mapper.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证服务类
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthMapper userAuthMapper;


    /**
     * 用户认证
     * @param username
     * @return
     */
    public Map<String,Object> login(String username){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("username",username);
        return userAuthMapper.login(parameterObject);
    }

    /**
     * 得到认证用户角色
     * @param username
     * @return
     */
    public List<HashMap> getAuthUserRole(String username){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("username",username);
        return userAuthMapper.getAuthUserRole(parameterObject);
    }
    /**
     * 得到用户认证信息
     * @param userid
     * @return
     */
    public Map<String,Object> getUserAuthInfoById(String userid){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("userid",userid);
        return userAuthMapper.getUserAuthInfoById(parameterObject);
    }
}
