package com.kang.postmodel9002.controller;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.service.GetPostsByRecommendPostNameService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 通过帖子名获取指定帖子的信息
 */
@Controller
public class GetPostsByRecommendPostNameController {
    @Autowired
    GetPostsByRecommendPostNameService getPostsByRecommendPostNameService;
    @RequestMapping("/getRecommendPosts")
    @ResponseBody
    @CrossOrigin
    public List<Post> getRecommendPosts(@RequestBody List<String> topThreePost){
       return getPostsByRecommendPostNameService.getPostsByPostName(topThreePost);
    }
}
