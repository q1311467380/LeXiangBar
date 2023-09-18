package com.kang.barmodel9001.controller;

import com.kang.barmodel9001.server.TestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 结合attentionmodel做个测试
 */
@Controller
public class TestController {
    @Autowired
    TestServer testServer;
    @RequestMapping("/test1/{id}")
    @ResponseBody
    public String test1(@PathVariable Integer id){
       String barName =  testServer.getBarName(id);

        return barName;
    }
}
