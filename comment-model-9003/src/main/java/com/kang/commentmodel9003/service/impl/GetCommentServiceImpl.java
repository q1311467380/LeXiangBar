package com.kang.commentmodel9003.service.impl;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.mapper.CommentMapper;
import com.kang.commentmodel9003.service.GetCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 根据帖名获得所有相关评论
 * 测试通过
 */
@Service
@Slf4j
public class GetCommentServiceImpl implements GetCommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<Comment> getAllCommentByPostName(String postName) {
        List<Comment> commentList = commentMapper.getAllCommentByPostName(postName);
        log.info("评论：" + commentList);
        return commentList;
    }
}
