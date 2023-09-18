package com.kang.usermodel9000.service;

import com.kang.beanmodel.bean.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
public interface MyInfoService {
    User getMyInfo(HttpServletRequest req);
}
