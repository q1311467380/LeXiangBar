package com.kang.recommendmodel9005.service;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AfterLoginRecommendService {
    List<ResponseData<Post>> afterLoginRecommend(HttpServletRequest req);
}
