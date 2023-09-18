package com.kang.recommendmodel9005.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.Preference;
import com.kang.beanmodel.bean.User;
import com.kang.recommendmodel9005.annotation.CheckToken;
import com.kang.recommendmodel9005.mapper.PreferenceMapper;
import com.kang.recommendmodel9005.service.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

/**
 * 插入操作得有用户的信息（用户名）
 */
@Service
@Slf4j
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    PreferenceMapper preferenceMapper;
    @Override
    public void setPreference(String[] selectedPreferences, HttpServletRequest req) {
//        User user = JSONObject.parseObject(req.getHeader("userInfo"), User.class);
//        String username = user.getUsername();

        Jedis resource = jedisPool.getResource();
        String username = req.getHeader("username");
        String realUsername = username.replace("\"", "");
        String userInfo = resource.get("user:" + realUsername);
        User user = JSONObject.parseObject(userInfo, User.class);
        //只能选3个偏好， 所以插入3次
        //但得先判断是否已经设置过偏好,如果有就先删再加
        for (int i = 0; i < selectedPreferences.length; i++) {
            Preference preference = preferenceMapper.selectPreferenceByUsernameAndKindName(realUsername,selectedPreferences[i]);
            if (preference != null){
                preferenceMapper.deletePreferenceByUsername(realUsername);
            }
        }
        for (int i = 0; i < selectedPreferences.length; i++) {
            preferenceMapper.insertPreference(realUsername,selectedPreferences[i]);
            log.info("barType:" + selectedPreferences[i]);
            resource.sadd("userPreferenceBarType-" + realUsername,selectedPreferences[i]);//把偏好写到redis中推荐要用到，后面可以加一个消息队列实现延迟双删
        }
        resource.close();
    }
}
