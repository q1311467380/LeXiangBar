package com.kang.recommendmodel9005;

import com.kang.recommendmodel9005.mapper.PreferenceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class RecommendModel9005ApplicationTests {

    @Test
    void contextLoads() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("kang",123);
        map.put("wnag",321);
        System.out.println(map.keySet());
    }

    @Autowired
    PreferenceMapper preferenceMapper;
    @Test
    void getTime(){
        System.out.println(preferenceMapper.getTime("b11").getTime());

    }

}
