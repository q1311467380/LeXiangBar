package com.kang.attentionmodel9004;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class AttentionModel9004ApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void test(){
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        System.out.println(timestamp);
    }
    @Test
    void test2(){

    }

}
