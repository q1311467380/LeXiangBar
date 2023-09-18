package com.kang.usermodel9000.service;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface SignUpService {
    ResponseData<User> signUp(User user, HttpServletRequest req);
}
