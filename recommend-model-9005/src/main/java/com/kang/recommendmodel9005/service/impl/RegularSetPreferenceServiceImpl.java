package com.kang.recommendmodel9005.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.User;
import com.kang.recommendmodel9005.service.RegularSetPreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Service
@Slf4j
public class RegularSetPreferenceServiceImpl implements RegularSetPreferenceService {
    @Autowired
    JedisPool jedisPool;
    @Override
    public void setExpireTime(HttpServletRequest req) {
//        String header = req.getHeader("userInfo");
//        User user = JSONObject.parseObject(header, User.class);
        Jedis resource = jedisPool.getResource();
        String username = req.getHeader("username");
        System.out.println("username:" + username);
        String realUsername = username.replace("\"", "");
        String userInfo = resource.get("user:" + realUsername);

        System.out.println("userInfo:" + userInfo);
        User user = JSONObject.parseObject(userInfo, User.class);
        System.out.println("user"+ user);
        resource.hset("setPreferenceTime", user.getUsername(), String.valueOf(System.currentTimeMillis() + 86400000));
//        resource.expire("setPreferenceTime",5184000);//设置过期时间为2个月，用户在一个月到两个月的时候登录一下就可以刷新过期时间，负责有其它逻辑
        resource.close();
    }

    @Override
    public Long getExpireTime(HttpServletRequest req) {
//        String header = req.getHeader("userInfo");
//        User user = JSONObject.parseObject(header, User.class);
        Jedis resource = jedisPool.getResource();
        String username = req.getHeader("username");
        String realUsername = username.replace("\"", "");
        System.out.println("usernameText:" + realUsername);
        String userInfo = resource.get("user:c1" );
        System.out.println("userInfoText:" + userInfo);
        User user = JSONObject.parseObject(userInfo, User.class);
        log.info("userName :" + user.getUsername());
        String setPreferenceTime = resource.hget("setPreferenceTime", user.getUsername());
        log.info(setPreferenceTime);
        Long expireTime = Long.valueOf(setPreferenceTime);
        log.info("到期时间：" + expireTime);
        resource.close();
        return expireTime;
    }
}
