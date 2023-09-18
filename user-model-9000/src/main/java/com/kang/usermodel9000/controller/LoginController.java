package com.kang.usermodel9000.controller;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;
import com.kang.usermodel9000.annotation.CheckToken;
import com.kang.usermodel9000.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

/**
 *用户登录的服务端部分逻辑
 * 查询前端请求（来自点击事件的异步请求）
 * 传回查询的结果前端根据结果进行下一步逻辑
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/login")
    @ResponseBody
    @CrossOrigin//允许跨域请求的注解
    @CheckToken
    public ResponseData<String> login(@RequestBody User user, HttpServletRequest req){
        ResponseData<String> result = loginService.checkUser(user,req);
        log.info("用户查询结果：" + result);
        return result;
    }

    /**
     * 登录加盐
     * 借助redis存储用户唯一标识：IP地址
     * 使用uuid产生的动态字符串为key
     * @return
     */
    @RequestMapping("/loginAddSalt")
    @ResponseBody
    @CrossOrigin//允许跨域请求的注解
    public String loginAddSalt(HttpServletRequest req){
        String salt = UUID.randomUUID().toString();
        Jedis resource = jedisPool.getResource();
        String remoteAddr = req.getRemoteAddr();
        System.out.println(remoteAddr);
        resource.set("userCache-" + salt,remoteAddr);
        return salt;
    }

}
