package com.kang.recommendmodel9005.service.impl;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;
import com.kang.recommendmodel9005.openFeign.PostServiceFeign;
import com.kang.recommendmodel9005.service.BarRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 按发布时间和评论时间的早晚排序推荐
 * 用一个redis的zset对象存储帖子的最后回复时间
 * 刚发布的时候最后回复时间就是发布时间
 * 发帖的时候加入对象，评论的时候更新
 * 注意分类（贴吧）
 */
@Service
@Slf4j
public class BarRecommendServiceImpl implements BarRecommendService {
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    JedisPool jedisPool;
    @Override
    public List<ResponseData<Post>> getRecommendPost(String barName) {
        Jedis resource = jedisPool.getResource();
        Set<String> zRange = resource.zrange("lastCommentTime-" + barName, 0, -1);
        List<String> postNames = new ArrayList<>();
        postNames.addAll(zRange);
        List<Post> recommendPosts = postServiceFeign.getRecommendPosts(postNames);
        List<ResponseData<Post>> recommends = new ArrayList<>();
        for (Post post : recommendPosts) {
            ResponseData<Post> postResponseData = new ResponseData<>();
            postResponseData.setData(post);
            String filePath = post.getFilePath();
            //获得文件的后缀名
            String[] parts = filePath.split("\\.");
            log.info("文件后缀名：" + parts[parts.length-1]);
            String type = parts[parts.length-1];
            //判断文件类型
            if (type.equalsIgnoreCase("mp4") || type.equalsIgnoreCase("avi")|| type.equalsIgnoreCase("mov")) {
                // 文件是视频
                postResponseData.setMessage("video");
                log.info("video");
            } else if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png")|| type.equalsIgnoreCase("gif")) {
                // 文件是图片
                postResponseData.setMessage("picture");
                log.info("picture");
            } else {
                // 其他文件类型,其它类型不显示，但仍然提供下载
                postResponseData.setMessage("other");
                log.info("other");
            }
            recommends.add(postResponseData);
        }
        resource.close();
        return recommends;
    }
}
