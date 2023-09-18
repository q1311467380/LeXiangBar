package com.kang.commentmodel9003.service;

import com.kang.beanmodel.bean.Comment;

import javax.servlet.http.HttpServletRequest;

public interface PublishCommentService {
    String publishComment(Comment comment, HttpServletRequest req);
}
