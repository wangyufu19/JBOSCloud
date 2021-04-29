package com.jbocloud.consumer.service;

import com.jboscloud.openapi.request.UserMgrRequest;
import org.springframework.cloud.openfeign.FeignClient;
/**
 * UserMgrService
 * @author youfu.wang
 * @date 2021-04-29
 */
@FeignClient("support")
public interface UserMgrService extends UserMgrRequest {


}


