package com.kang.usermodel9000.service.impl;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.Code;
import com.kang.beanmodel.util.ResponseData;
import com.kang.usermodel9000.mapper.UserMapper;
import com.kang.usermodel9000.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 根据前端传入的用户名查找是否已经存在
 * 存在：返回一个标志，提醒前端返回用户名已存在
 * 不存在：插入用户表中
 * 并登记用户的注册时间到redis缓存中的setPreferenceTime的hash集合中，用于定期偏好设置
 * 插入后返回用户的相关信息
 */
@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public ResponseData<User> signUp(User user, HttpServletRequest req) {
        ResponseData<User> responseData = new ResponseData<User>();
        String username = user.getUsername();
        String result = userMapper.selectPasswordByUsername(username);//根据传回来的密码是否为空判断用户是否存在，密码非空的
        if (result != null) {
            responseData.setCode(Code.USER_EXIT);//用户已存在11002
            return responseData;

        } else {
            int index = user.getPassword().indexOf("-");
            user.setPassword(user.getPassword().substring(0,index));
            boolean insert = userMapper.insertUser(user);
            responseData.setCode(Code.SUCCEED_SIGN_UP);//注册成功11001
            responseData.setData(user);
            Jedis resource = jedisPool.getResource();
            resource.hset("setPreferenceTime",username, String.valueOf(System.currentTimeMillis()));//设置偏好设置周期的初始时间
//            resource.expire("setPreferenceTime",5184000);//设置过期时间为2个月，用户在一个月到两个月的时候登录一下就可以刷新过期时间，负责有其它逻辑
            log.info("time:" + resource.hget("setPreferenceTime",username));
            resource.close();
            return responseData;
        }
    }

}
