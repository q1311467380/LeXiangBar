package com.kang.usermodel9000.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.bean.User;
import com.kang.usermodel9000.mapper.UserMapper;
import com.kang.usermodel9000.service.MyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class MyInfoServiceImpl implements MyInfoService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserMapper userMapper;
    @Override
    public User getMyInfo(HttpServletRequest req) {
//        String userInfo = req.getHeader("userInfo");
//        log.info("userInfo:" + userInfo);
//
////        String username = WebUtils.getCookie(req, "username").getValue();
//        //获得了数据库中用户的信息还不够，还有用户发布的帖子取后3个（最近写的3个帖子）
//
//        User user1 = JSONObject.parseObject(userInfo, User.class);
//        String username = user1.getUsername();
//        log.info("username:" + username);
//        User user = userMapper.getUserByUsername(username);
        Jedis resource = jedisPool.getResource();
        String username = req.getHeader("username");
        String realUsername = username.replace("\"", "");
        String userInfo = resource.get("user:" + realUsername);
        User user = JSONObject.parseObject(userInfo, User.class);
        //使用restTemplate远程调用post模块，也可以直接导入依赖使用
        //username是作为请求体发送的
        List<Post> postList = restTemplate.postForObject("http://localhost:9002/getRecentlyPublish",realUsername,List.class);
        user.setPostsList(postList);
        System.out.println(user);
        return user;
    }
}
