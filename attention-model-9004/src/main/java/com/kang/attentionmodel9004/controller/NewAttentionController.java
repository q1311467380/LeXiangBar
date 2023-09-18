package com.kang.attentionmodel9004.controller;

import com.alibaba.fastjson2.JSONObject;
import com.kang.attentionmodel9004.service.NewAttentionService;
import com.kang.beanmodel.bean.Bar;
import com.kang.beanmodel.bean.Fans;
import com.kang.beanmodel.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 关注
 * 根据前端传入的 用户名，吧名，关注时间
 * 插入前先判断是否该用户已经关注
 * 关注了就取消关注，否则就关注
 * 插入到fans表中，从而影响“通知”逻辑
 *
 */
@Controller
@Slf4j
public class NewAttentionController {
    @Autowired
    NewAttentionService newAttentionService;
    @RequestMapping("/newAttention")
    @ResponseBody
    @CrossOrigin
    public String newAttention(HttpServletRequest req){
        return newAttentionService.newFans(req);
    }


}
