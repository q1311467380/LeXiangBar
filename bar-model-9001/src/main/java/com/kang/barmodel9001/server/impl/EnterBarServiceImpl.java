package com.kang.barmodel9001.server.impl;

import com.kang.barmodel9001.mapper.BarMapper;
import com.kang.barmodel9001.server.EnterBarService;
import com.kang.beanmodel.bean.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 根据传入的bar名，获取对应吧的相关信息，方便用户使用，提高用户体验
 */
@Service
public class EnterBarServiceImpl implements EnterBarService {
    @Autowired
    BarMapper barMapper;
    @Override
    public Bar getBarInfo(String barName) {
        return barMapper.selectBarByBarName(barName);
    }
}
