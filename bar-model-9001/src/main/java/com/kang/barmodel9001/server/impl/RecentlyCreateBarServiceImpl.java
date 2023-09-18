package com.kang.barmodel9001.server.impl;

import com.kang.barmodel9001.mapper.BarMapper;
import com.kang.barmodel9001.server.RecentlyCreateBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *根据请求请求中cookie携带的userId
 * 从数据库获取最近时间关注的2个贴吧（如果小于2个，有几个就要几个）的名字和描述
 */
@Service
public class RecentlyCreateBarServiceImpl implements RecentlyCreateBarService {
    @Autowired
    BarMapper barMapper;
    @Override
    public Bar getRecentlyCreate(Integer userId) {
        return barMapper.getRecentlyCreateById(userId);
    }
}
