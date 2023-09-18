package com.kang.postmodel9002.service;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;

import javax.servlet.http.HttpServletRequest;

public interface GetPostInfoService {
    ResponseData<User> getPost(String postName,HttpServletRequest req);

}
