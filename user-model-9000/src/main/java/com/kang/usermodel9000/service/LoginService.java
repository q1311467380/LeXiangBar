package com.kang.usermodel9000.service;

import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */

public interface LoginService {

    ResponseData<String> checkUser(User user, HttpServletRequest req);

    Integer getUserId(String username);
}
