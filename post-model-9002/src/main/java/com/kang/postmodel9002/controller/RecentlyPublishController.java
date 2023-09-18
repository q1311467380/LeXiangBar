package com.kang.postmodel9002.controller;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.RecentlyPublishPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询最近用户最近发表的帖子
 */
@Controller
public class RecentlyPublishController {
    @Autowired
    RecentlyPublishPostService recentlyPublishPostService;
    @RequestMapping("/getRecentlyPublish")
    @ResponseBody
    @CrossOrigin
    public List<Post> getRecentlyPublish(@RequestBody String username){
        return recentlyPublishPostService.getRecentlyPost(username);
    }
}
