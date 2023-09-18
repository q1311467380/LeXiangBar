package com.kang.commentmodel9003.service.impl;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.mapper.CommentMapper;
import com.kang.commentmodel9003.service.PublishCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.jar.JarEntry;

/**
 * 发表评论
 */
@Service
@Slf4j
public class PublishCommentServiceImpl implements PublishCommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public String publishComment(Comment comment, HttpServletRequest req) {
        //设置评论发表的时间，用于帖子推荐
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
        if (commentMapper.insertComment(comment)){
            /*
            每发一条评论就在redis中记录，以便推荐的时候取用
            使用zset存储一天内的帖子的评论量，当然还有一个永久记录帖子评论数的zset但是暂时用不上
             */

            Jedis resource = jedisPool.getResource();
            //发帖的时候吧帖子加入到该zSet对象中，按帖子的名字
            //然后发评论，就加分数（浏览次数）
            //问题：过期时间的设置
            resource.zincrby("commentNumber",1,comment.getPostName());
            log.info(String.valueOf(resource.zrangeWithScores("commentNumber",0,10)));
            //因为查询的数据小对服务器的压力小，而如果发送请求到post模块查询也会对服务器造成一定压力，而且post的服务器也有压力，综合起来直接查询会好一点
            String barName = commentMapper.getBarNameByPostName(comment.getPostName());
            System.out.println("barName:" + barName);
            System.out.println("postName:" + comment.getPostName());
            resource.zadd("lastCommentTime-" + barName,System.currentTimeMillis(), comment.getPostName());
            resource.close();
            return "1";
        }
        return "0";
    }
}
