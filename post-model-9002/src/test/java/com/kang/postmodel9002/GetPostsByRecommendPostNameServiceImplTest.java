package com.kang.postmodel9002;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class GetPostsByRecommendPostNameServiceImplTest {
    @Autowired
    PostMapper postMapper;
    @Test
    public void getPostsTest(){
        List<String> posts = new ArrayList<>();
        posts.add("newPost4");
        posts.add("newPost5");
        for (String post : posts) {
            log.info(String.valueOf(postMapper.selectPostByName(post)));
        }

    }
}
