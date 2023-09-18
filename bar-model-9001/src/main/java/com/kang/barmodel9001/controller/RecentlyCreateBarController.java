package com.kang.barmodel9001.controller;

import com.kang.barmodel9001.server.RecentlyCreateBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 动态显示到前端concern页面上我的创建部分
 * 还是因为cookie携带不过来的问题导致无法测试
 */
@Controller
public class RecentlyCreateBarController {
    @Autowired
    RecentlyCreateBarService recentlyCreateBarService;
    @RequestMapping("/getMyCreate")
    @ResponseBody
    @CrossOrigin
    public Bar getMyCreate(HttpServletRequest req){
        Integer userId = Integer.valueOf(WebUtils.getCookie(req, "userId").getValue());
        return recentlyCreateBarService.getRecentlyCreate(userId);
    }
}
