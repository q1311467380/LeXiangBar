package com.kang.commentmodel9003.service;

import com.kang.beanmodel.bean.Comment;

import java.util.List;

public interface GetCommentService {
    List<Comment> getAllCommentByPostName(String postName);
}
