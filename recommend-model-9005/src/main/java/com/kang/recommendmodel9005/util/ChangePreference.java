package com.kang.recommendmodel9005.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 用户设置的偏好和其实际喜好不符
 * 改变对其的推荐（改变userPreferenceBarType对象）
 * 判断标准：当用户浏览总量来到1200（这里用12来进行测试），如果设置的3个偏好类别的浏览数达不到总数一半
 * 进行以下操作：重新设置用户的偏好
 * 并清空浏览量---直接删（优化记录下上一次的浏览量，然后用增量进行判断，可以保留一直以来各类帖子的浏览量，但是目前用不到）
 */
@Component
public class ChangePreference {
    @Autowired
    JedisPool jedisPool;

    //定期判断--一天
    @Scheduled(fixedRate = 86400000)
    public void reSetPreference() {
        Jedis resource = jedisPool.getResource();
        Set<Tuple> allBarTypeViewNumber = resource.zrangeWithScores("barTypeViewNumber", 0, -1);
        Set<String> userPreferenceBarType = resource.smembers("userPreferenceBarType");//当前偏好
        double total = 0.0;//用户的总浏览量
        double preferenceTotal = 0.0;//偏好类别的浏览量
        //计算两者浏览量
        for (Tuple tuple : allBarTypeViewNumber) {
            String bayType = tuple.getElement();
            double score = tuple.getScore();
            //计算偏好类别的总浏览量
            for (String preferenceType : userPreferenceBarType) {
                if (preferenceType.equals(bayType)) {
                    preferenceTotal += score;
                }
            }
            total += score;//计算用户浏览总量
        }
        //大于阈值开启操作
        if (total >= 1200.0) {
            //触发重新修改偏好的条件
            if (preferenceTotal < total / 2) {
                resource.del("userPreferenceBarType");//先清除当前偏好
                Set<Tuple> topThreeType = resource.zrangeWithScores("barTypeViewNumber", 0, 2);//获取浏览量最多的3个帖子，当成新的偏好
                //再写入新的偏好
                for (Tuple tuple : topThreeType) {
                    resource.sadd("userPreferenceBarType", tuple.getElement());
                }
                //然后清空所有浏览量
                resource.del("barTypeViewNumber");
            }
        }
        resource.close();
    }

}
