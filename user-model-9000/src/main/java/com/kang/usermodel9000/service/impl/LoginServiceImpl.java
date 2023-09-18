package com.kang.usermodel9000.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.Code;
import com.kang.beanmodel.util.ResponseData;
import com.kang.usermodel9000.mapper.UserMapper;
import com.kang.usermodel9000.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实际处理服务端用户登录逻辑的类
 * 三种情况
 * 用户名不存在：不存在查询结果为空null
 * 密码错误： 判断根据正确的用户名查询的密码与传入的密码是否相同，返回空
 * 登录成功： 根据用户名查找用户，再把用户传回js页面
 * 因为还要携带一些用户的相关信息到前端，所以用自定义的响应体，
 * 三种情况的状态码使用 code接收
 * 用户相关的数据使用data接收
 *
 *
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    UserMapper userMapper;
    @Override
    public ResponseData<String> checkUser(User user, HttpServletRequest req) {
        ResponseData<String> loginResponseData = new ResponseData<>();

        //因为是使用ip作为用户的唯一标识，所以先判断ip是为该用户存储的ip
        Jedis resource = jedisPool.getResource();
        String loginSalt = req.getHeader("loginSalt");
        System.out.println(loginSalt);
        String realIp = resource.get("userCache-" + loginSalt);
        System.out.println("用户ip"  + realIp);
        String currentIp = req.getRemoteAddr();
        System.out.println("当前访问者的ip" + currentIp);
        if (!realIp.equals(currentIp)){
            loginResponseData.setCode(Code.ERROR_LOGIN);
            return loginResponseData;
        }
        String thickenPassword = user.getPassword();//加密的密码，需要处理取出正确的密码
        System.out.println("未处理前密码" + thickenPassword);
        int index = thickenPassword.indexOf(":");
        user.setPassword(thickenPassword.substring(0,index));
        System.out.println("解析出来的密码：" + user.getPassword());
        String username = user.getUsername();
        log.info("传入的用户名：" + username.equals("kang"));
        String realPassword = userMapper.selectPasswordByUsername(username);
        log.info("查询的密码：" + realPassword);
        //查询的密码如果不等于空就是用户名正确
        if (realPassword != null){
            String transferPassword = user.getPassword();//用户填(传)的密码
            //判断传入的密码是否正确
            if (transferPassword.equals(realPassword)){
                //密码正确
                log.info("密码正确");
                loginResponseData.setCode(Code.SUCCEED_LOGIN);
//                loginResponseData.setData(userMapper.getUserAllByUsername(username));
                loginResponseData.setData(username);
                //登陆成功，将用户信息存储到key（令牌）为用户名的redis set对象中
                User userInfo = userMapper.getUserAllByUsername(username);
                System.out.println("userInfo:" + userInfo);
                resource.set("user:" + username, JSONObject.toJSONString(userInfo));
                return loginResponseData;
            }else {
                //用户名正确，密码错误
                log.info("密码错误");
                loginResponseData.setCode(Code.ERROR_PASSWORD);
                return loginResponseData;
            }
        }
        //用户名错误
        log.info("用户名错误");
        loginResponseData.setCode(Code.ERROR_USERNAME);
        return loginResponseData;
    }

    @Override
    public Integer getUserId(String username) {
        Integer userId = userMapper.getUserIdByUsername(username);
        return userId;
    }
}
