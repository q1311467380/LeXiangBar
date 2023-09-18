package com.kang.attentionmodel9004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * 框架搭建的测试
 */
@Controller
public class TestController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        String result = restTemplate.getForObject("http://localhost:9001/test1/1",String.class);
        return result;
    }
}
