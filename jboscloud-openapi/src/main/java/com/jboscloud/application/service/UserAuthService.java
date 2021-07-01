package com.jboscloud.application.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证服务类
 */

public interface UserAuthService {
    /**
     * 用户认证
     * @param username
     * @return
     */
    public Map<String,Object> login(String username);
    /**
     * 得到用户认证信息
     * @param userid
     * @return
     */
    public Map<String,Object> getUserAuthInfoById(String userid);
    /**
     * 得到认证用户角色
     * @param username
     * @return
     */
    public List<HashMap> getAuthUserRole(String username);
}
