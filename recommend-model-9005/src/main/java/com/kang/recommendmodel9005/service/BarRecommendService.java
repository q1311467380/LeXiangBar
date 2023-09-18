package com.kang.recommendmodel9005.service;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;

import java.util.List;

public interface BarRecommendService {
    List<ResponseData<Post>> getRecommendPost(String barName);
}
