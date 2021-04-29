package com.jbocloud.consumer.service.fallback;

import com.jbocloud.consumer.service.UserMgrService;
import com.jboscloud.common.Return;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * UserMgrServiceFallback
 * @author youfu.wang
 * @date 2021-04-29
 */
@Component
public class UserMgrServiceFallback implements UserMgrService {

    @Override
    public Map<String, Object> getUserInfo(String username, String password, String desc) {
        return Return.error();
    }
}
