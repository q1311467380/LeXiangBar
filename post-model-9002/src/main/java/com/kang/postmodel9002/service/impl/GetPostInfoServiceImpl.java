package com.kang.postmodel9002.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.Comment;
import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.GetPostInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * 帖子页面
 *根据用户名和帖子名，获取相关信息
 * 用户名
 * 帖子名
 * 帖子内容（涉及到文件不就得用到下载了）
 * 评论
 */
@Service
@Slf4j
public class GetPostInfoServiceImpl implements GetPostInfoService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    PostMapper postMapper;
    @Autowired
    RestTemplate restTemplate;
    @Override
    public ResponseData<User> getPost( String postName,HttpServletRequest req) {
        ResponseData<User> responseData = new ResponseData<>();

//        String userInfo = req.getHeader("userInfo");
//        User user = JSONObject.parseObject(userInfo, User.class);
        Jedis resource = jedisPool.getResource();
        String username = req.getHeader("username");
        String realUsername = username.replace("\"", "");
        String userInfo = resource.get("user:" + realUsername);
        User user = JSONObject.parseObject(userInfo, User.class);
        log.info("username:" + realUsername);
//        String postInfo = req.getHeader("postInfo");
//        Post post1 = JSONObject.parseObject(postInfo, Post.class);
//        String postName = post1.getPostName();
        log.info("postName:" + postName);
        //获得帖子内容放入user种并对内容的类型进行判断
        Post post = postMapper.getPostByUsernameAndPostName(realUsername,postName);
        log.info("postInfo:" + post);
        List<Post> postList = new ArrayList<Post>();
        postList.add(post);
        user.setPostsList(postList);
//        String content = post.getContent();
        String filePath = post.getFilePath();
        //获得文件的后缀名
        String[] parts = filePath.split("\\.");
        log.info("文件后缀名：" + parts[parts.length-1]);
        String type = parts[parts.length-1];
        //判断文件类型
        if (type.equalsIgnoreCase("mp4") || type.equalsIgnoreCase("avi")|| type.equalsIgnoreCase("mov")) {
            // 文件是视频
            responseData.setMessage("video");
            log.info("video");
        } else if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png")|| type.equalsIgnoreCase("gif")) {
            // 文件是图片
            responseData.setMessage("picture");
            log.info("picture");
        } else {
            // 其他文件类型,其它类型不显示，但仍然提供下载
            responseData.setMessage("other");
            log.info("other");
        }
        //获取评论,并放入user
        List<Comment> postCommentList = restTemplate.getForObject("http://localhost:9003/getPostComment?postName=" + postName, List.class);
        log.info(String.valueOf(postCommentList));
        user.setCommentsList(postCommentList);
        responseData.setData(user);
        return responseData;
    }
}
