package com.kang.recommendmodel9005.controller;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.User;
import com.kang.recommendmodel9005.service.RegularSetPreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

/**
 * 定期设置偏好的服务端逻辑
 * 在redis中用hash存储所有用户的到期设置时间
 */
@Controller
@Slf4j
public class RegularSetPreferenceController {
    @Autowired
    RegularSetPreferenceService regularSetPreferenceService;

    @RequestMapping("/setExpireTime")
    @ResponseBody
    @CrossOrigin
    public boolean setExpireTime(HttpServletRequest req){
        regularSetPreferenceService.setExpireTime(req);
        return true;
    }
    @RequestMapping("/getExpireTime")
    @ResponseBody
    @CrossOrigin
    public Long getExpireTime(HttpServletRequest req){
        return  regularSetPreferenceService.getExpireTime(req);
    }
}
