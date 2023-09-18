package com.kang.postmodel9002;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.GetPostInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class GetPostInfoTest {
    @Autowired
    PostMapper postMapper;
    @Test
    public void getPostTest(){
        Post post = postMapper.getPostByUsernameAndPostName("kang","newPost1");
        List<Post> postList = new ArrayList<Post>();
        postList.add(post);
        for (Post post1 : postList) {
            log.info(String.valueOf(post1));
        }
    }
}
