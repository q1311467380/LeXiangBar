package com.kang.recommendmodel9005.controller;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;
import com.kang.recommendmodel9005.service.AfterLoginRecommendService;
import com.kang.recommendmodel9005.service.BeforeLoginRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Controller
public class AfterLoginRecommendController {
    @Autowired
    AfterLoginRecommendService afterLoginRecommendService;
    @RequestMapping("/afterLoginRecommendPost")
    @ResponseBody
    @CrossOrigin
    public List<ResponseData<Post>> afterLoginRecommendPost(HttpServletRequest req){
        return afterLoginRecommendService.afterLoginRecommend(req);
    }
}
