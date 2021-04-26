package com.jbocloud.consumer.service;

import com.google.gson.Gson;
import com.jboscloud.common.Return;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMgrService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserInfoFallback")
    public Return getUserInfo(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("username", "admin");
        params.add("desc", "超级管理员");
        params.add("password", "123456");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity("http://SUPPORT/getUserInfo",requestEntity,String.class);
        Gson gson=new Gson();
        HashMap<String,Object> map=gson.fromJson(responseEntity.getBody(), HashMap.class);
        Return ret=new Return();
        return ret;
    }
    public Return getUserInfoFallback(){
        return Return.error();
    }
}


