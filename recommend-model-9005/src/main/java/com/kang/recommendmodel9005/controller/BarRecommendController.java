package com.kang.recommendmodel9005.controller;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;
import com.kang.recommendmodel9005.service.BarRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 根据barName查询三天内的有最近评论时间的3个帖子
 */
@Controller
public class BarRecommendController {
    @Autowired
    BarRecommendService barRecommendService;
    @RequestMapping("/getBarRecommend")
    @ResponseBody
    @CrossOrigin
    public List<ResponseData<Post>> getBarRecommend(@RequestParam String barName){
        return barRecommendService.getRecommendPost(barName);
    }
}
