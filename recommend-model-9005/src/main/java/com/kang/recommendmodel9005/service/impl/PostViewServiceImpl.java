package com.kang.recommendmodel9005.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.recommendmodel9005.mapper.AfterLoginRecommendMapper;
import com.kang.recommendmodel9005.service.PostViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

/**
 *一段时间内帖子的总访问量存储对象--postViewNumber
 * 帖子所在贴吧的贴吧类的浏览量--barTypeViewNumber
 */
@Service
@Slf4j
public class PostViewServiceImpl implements PostViewService {
    @Autowired
    AfterLoginRecommendMapper afterLoginRecommendMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public Double increase(HttpServletRequest req) {
        String viewPostName1 = req.getHeader("viewPostName");
        //得到的请求头是string类型的所以不能再转成String了
//        String postName = JSONObject.parseObject(viewPostName1, String.class);
//        String viewPostName = JSONObject.parseObject(req.getHeader("viewPostName"), String.class);
        Jedis resource = jedisPool.getResource();
        Double postViewNumber = resource.zincrby("postViewNumber", 1, viewPostName1);//一段时间内的浏览数加1并返回增加后的浏览数
        String bayTypeByPostName = afterLoginRecommendMapper.getBayTypeByPostName(viewPostName1);
        resource.zincrby("barTypeViewNumber",1,bayTypeByPostName);//帖子所在贴吧的贴吧类的浏览量
        log.info("所有bar类的浏览量：" + resource.zrangeWithScores("barTypeViewNumber",0,-1));
        resource.close();
        return postViewNumber;
    }
}
