package com.kang.barmodel9001.controller;

import com.kang.barmodel9001.server.EnterBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户进入指定吧页面时
 * 页面数据的动态显示
 * 还有关于一些贴吧信息的返回，在前端进行持久化，实现多端口之间的共享
 */
@Controller
public class EnterBar {
    @Autowired
    EnterBarService enterBarService;
    @RequestMapping("/enterBar")
    @ResponseBody
    @CrossOrigin
    public Bar enterBar(@RequestParam String barName){
      return enterBarService.getBarInfo(barName);
    }
}
