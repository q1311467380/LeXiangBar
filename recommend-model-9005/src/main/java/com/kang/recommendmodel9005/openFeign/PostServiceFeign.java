package com.kang.recommendmodel9005.openFeign;

import com.kang.beanmodel.bean.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *关于post模块的远程调用
 */
@FeignClient("post-server")
@Component
public interface PostServiceFeign {
    @RequestMapping("/getRecommendPosts")
    @ResponseBody
    @CrossOrigin
    List<Post> getRecommendPosts(@RequestBody List<String> topThreePost);
}
