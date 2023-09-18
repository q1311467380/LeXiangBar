package com.kang.barmodel9001.mapper;

import com.kang.beanmodel.bean.Bar;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface BarMapper {

    boolean createNewBar(Bar bar);

    Bar selectBarByBarName(String barName);

    Bar getRecentlyCreateById(Integer userId);
}
