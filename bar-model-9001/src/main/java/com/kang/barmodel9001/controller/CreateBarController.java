package com.kang.barmodel9001.controller;

import com.kang.barmodel9001.server.CreateBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 创建新吧
 */
@Controller
public class CreateBarController {

    @Autowired
    CreateBarService createBarService;
    @RequestMapping(value = "/createBar",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String createBar(@RequestBody Bar bar){
        return createBarService.createNewBar(bar);
    }
}
