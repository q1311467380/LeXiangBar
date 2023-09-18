package com.kang.postmodel9002.service.impl;

import com.kang.beanmodel.bean.Post;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.RecentlyPublishPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class RecentlyPublishPostServiceImpl implements RecentlyPublishPostService {
    @Autowired
    PostMapper postMapper;
    @Override
    public List<Post> getRecentlyPost(String username) {
        List<Post> recentlyPostByUsername = postMapper.getRecentlyPostByUsername(username);
        for (Post post : recentlyPostByUsername) {
            System.out.println(post);
        }
        return recentlyPostByUsername;
    }
}
