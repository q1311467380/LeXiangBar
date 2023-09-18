package com.kang.usermodel9000.controller;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;
import com.kang.usermodel9000.service.LoginService;
import com.kang.usermodel9000.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;


/**
 * 注册逻辑
 */
@Controller
public class SignUpController {
    @Autowired
    SignUpService signUpService;
    @Autowired
    LoginService loginService;
    @RequestMapping("/signUp")
    @ResponseBody
    @CrossOrigin
    public ResponseData<User> signUp(@RequestBody User user, HttpServletRequest req){
        //注册完成也会跳转到after页面所以也要上传用户的相关信息
        return signUpService.signUp(user,req);
    }
    @RequestMapping("/signUpAddSalt")
    @ResponseBody
    @CrossOrigin
    public String signUpAddSalt(){
        //注册完成也会跳转到after页面所以也要上传用户的相关信息
        return UUID.randomUUID().toString();
    }
}
