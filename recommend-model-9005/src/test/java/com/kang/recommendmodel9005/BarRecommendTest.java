package com.kang.recommendmodel9005;

import com.kang.recommendmodel9005.service.BarRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BarRecommendTest {
    /**
     * 测试通过
     */
    @Autowired
    BarRecommendService barRecommendService;
    @Test
    public void getPostTest(){
        barRecommendService.getRecommendPost("b2");
    }
}
