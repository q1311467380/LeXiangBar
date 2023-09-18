package com.kang.postmodel9002.service;

import com.kang.beanmodel.bean.Post;

import java.util.List;

/**
 * 
 */
public interface RecentlyPublishPostService {
    List<Post> getRecentlyPost(String username);
}
