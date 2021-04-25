package com.jbocloud.consumer.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserMgrController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("username", "admin");
        params.add("desc", "超级管理员");
        params.add("password", "123456");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> responseEntity=
                //restTemplate.getForEntity("http://SUPPORT/getUserInfo",String.class,"admin");
                restTemplate.postForEntity("http://SUPPORT/getUserInfo",requestEntity,String.class);
        Gson gson=new Gson();
        Map<String,Object> ret=gson.fromJson(responseEntity.getBody(), HashMap.class);
        return ret;
    }
}
