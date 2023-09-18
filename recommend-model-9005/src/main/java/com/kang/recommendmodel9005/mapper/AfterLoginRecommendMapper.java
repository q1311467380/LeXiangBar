package com.kang.recommendmodel9005.mapper;

import org.springframework.stereotype.Repository;

/**
 *单元测试通过
 */
@Repository
public interface AfterLoginRecommendMapper {
    String getBayTypeByPostName(String postNameTemp);
}
