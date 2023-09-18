package com.kang.recommendmodel9005;

import com.kang.recommendmodel9005.mapper.AfterLoginRecommendMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 */
@SpringBootTest
@Slf4j
public class AfterLoginRecommendTest {
    @Autowired
    AfterLoginRecommendMapper afterLoginRecommendMapper;
    @Test
    public void getBayTypeByPostName(){
        String bayTypeTest = afterLoginRecommendMapper.getBayTypeByPostName("cscs");
        log.info("贴吧类别：" + bayTypeTest);
    }
}
