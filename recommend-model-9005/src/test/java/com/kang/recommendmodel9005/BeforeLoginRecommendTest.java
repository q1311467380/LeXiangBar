package com.kang.recommendmodel9005;

import com.kang.recommendmodel9005.service.BeforeLoginRecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeforeLoginRecommendTest {
    @Autowired
    BeforeLoginRecommendService beforeLoginRecommendService;
    //推荐分结果测试
    @Test
    public void test(){
        beforeLoginRecommendService.beforeLoginRecommend();
    }
    @Test
    public void test2(){
        double d1 = 0.0;
        double d2 = 0.4;
        double d3 = d1 + d2;
        System.out.println(d3);
    }
}
