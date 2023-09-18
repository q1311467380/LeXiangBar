package com.kang.barmodel9001.server;

import com.kang.beanmodel.bean.Bar;

public interface RecentlyCreateBarService {

    Bar getRecentlyCreate(Integer userId);
}
