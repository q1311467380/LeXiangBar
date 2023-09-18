package com.kang.barmodel9001.server.impl;

import com.kang.barmodel9001.mapper.BarMapper;
import com.kang.barmodel9001.server.CreateBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 */
@Service
public class CreateBarServiceImpl implements CreateBarService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    BarMapper barMapper;
    @Override
    /*
    判断吧名是否存在
    先写入MySQL吧，redis稍后再说
     */
    public String createNewBar(Bar bar) {
        String barName = bar.getBarName();
        Bar bar1 = barMapper.selectBarByBarName(barName);
        //如果查询结果存在该吧 返回2 代表该吧名已存在
        if (bar1 != null){
            return "2";
        }
        boolean result = barMapper.createNewBar(bar);
        //创建成功为1 失败为0
        if (result){
//            //把新吧的数据写入redis中，频繁访问发帖
//            Jedis resource = jedisPool.getResource();
            return "1";
        }else {
            return "0";
        }
    }
}
