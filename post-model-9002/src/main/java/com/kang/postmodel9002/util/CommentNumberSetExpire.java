package com.kang.postmodel9002.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 定期清除过期的帖子---记录评论数和浏览量的可以一起删
 * 因为被推荐的帖子需要有时效性，取过期时间为1天
 */

@Component
public class CommentNumberSetExpire {
    @Autowired
    JedisPool jedisPool;
    //每30秒处理一次过期数据
    @Scheduled(fixedRate = 30000)
    public void deleteExpire(){
        Jedis resource = jedisPool.getResource();
        Map<String, String> commentTime = resource.hgetAll("commentTime");
        for (Map.Entry<String, String> post: commentTime.entrySet()) {
            String postName = post.getKey();
            long expireTime = Long.parseLong(post.getValue());//过期时间
            long currentTimeMillis = System.currentTimeMillis();
            // 判断是否过期，过期就删除（评论数和浏览量同时删）
            if (currentTimeMillis >= expireTime){
                resource.zrem("commentNumber",postName);
                resource.zrem("postViewNumber",postName);
                resource.hdel("comment",postName);
            }
            resource.close();
        }

    }
}
