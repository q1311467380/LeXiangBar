package com.kang.attentionmodel9004.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.attentionmodel9004.mapper.FansMapper;
import com.kang.attentionmodel9004.service.NewAttentionService;
import com.kang.beanmodel.bean.Bar;
import com.kang.beanmodel.bean.Fans;
import com.kang.beanmodel.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 插入新的粉丝记录
 * 判断是关注还是取消关注
 * 关注就算没关注过，取消关注就是已经关注了
 * 根据是否有用户名和贴吧名相同的关注
 */
@Service
@Slf4j
public class NewAttentionServiceImpl implements NewAttentionService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    FansMapper fansMapper;
    @Override
    public String newFans(HttpServletRequest req) {
        String userInfo = req.getHeader("userInfo");
        User user = JSONObject.parseObject(userInfo, User.class);
        String username = user.getUsername();
        String barInfo = req.getHeader("barInfo");
        Bar bar = JSONObject.parseObject(barInfo, Bar.class);
        String barName = bar.getBarName();
        Timestamp attentionTime = new Timestamp(System.currentTimeMillis());
        //判断该用户是否已经关注过
        List<String> barNameList = fansMapper.selectBarNameByUsername(username);
        for (String s : barNameList) {
            //关注过，删除关注
            log.info(s);
            if (barName.equals(s)){
                fansMapper.deleteAttentionByUsernameAndBarName(username,barName);
                return "0";
            }
        }
        Fans fans = new Fans(username,barName,attentionTime);
        log.info("Fans:" + fans);
        boolean result = fansMapper.insertNewFans(fans);
        //插入成功返回1，失败返回0
        log.info("result:" + result);
        if (result){
            //插入成功，贴吧关注人数计数
            Jedis resource = jedisPool.getResource();
            resource.zincrby("barAttentionNumber-" + barName, 1,barName);
            return "1";
        }
        return "2";
    }
}
