package com.kang.usermodel9000.controller;

import com.kang.beanmodel.bean.User;
import com.kang.usermodel9000.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 个人信息页面的动态内容显示
 */
@Controller
public class MyInfoController {
    @Autowired
    MyInfoService myInfoService;
    @RequestMapping("/myInfo")
    @ResponseBody
    @CrossOrigin
    public User myInfo(HttpServletRequest req){
        return myInfoService.getMyInfo(req);
    }
}
