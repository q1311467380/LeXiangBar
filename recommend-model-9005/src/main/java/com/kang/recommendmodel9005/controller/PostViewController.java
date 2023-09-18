package com.kang.recommendmodel9005.controller;

import com.kang.recommendmodel9005.service.PostViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统计浏览量
 * 浏览量分两类：
 * 帖子的总浏览量---来自：登录前页面，登录后，贴吧中三个页面
 * 某类贴吧的所有帖子的浏览量---来自：登录后，贴吧中两个页面
 * 每种有分两种情况：一段时间内的，所有的（因为推荐算法推荐的都是有时限的，所以暂时用不到总的）
 *一段时间内帖子的总访问量存储对象--postViewNumber
 */
@Controller
public class PostViewController {
    @Autowired
    PostViewService postViewService;
    @RequestMapping("/thePostViewNumber")
    @ResponseBody
    @CrossOrigin
    public Double thePostViewNumber(HttpServletRequest req){
        return postViewService.increase(req);
    }

}
