package com.kang.postmodel9002.service.impl;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.GetPostsByRecommendPostNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *单元测试通过
 */
@Service
public class GetPostsByRecommendPostNameServiceImpl implements GetPostsByRecommendPostNameService {
    @Autowired
    PostMapper postMapper;
    @Override
    public List<Post> getPostsByPostName(List<String> topThreePost) {
        List<Post> postList = new ArrayList<>();
        for (String postName : topThreePost) {
            Post post = postMapper.selectPostByName(postName);
            postList.add(post);
        }
        return postList;
    }
}
