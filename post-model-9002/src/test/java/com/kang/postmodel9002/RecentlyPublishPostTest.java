package com.kang.postmodel9002;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 *获取post操作正常
 */
@SpringBootTest
@Slf4j
public class RecentlyPublishPostTest {
    @Autowired
    PostMapper postMapper;
    @Test
    public void getRecentlyTest(){
        List<Post> kang = postMapper.getRecentlyPostByUsername("kang");
        for (Post post : kang) {
            log.info("post: " + post);
        }
    }
}
