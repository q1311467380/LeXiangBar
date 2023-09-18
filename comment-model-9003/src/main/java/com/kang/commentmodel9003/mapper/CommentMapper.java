package com.kang.commentmodel9003.mapper;

import com.kang.beanmodel.bean.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<Comment> getAllCommentByPostName(String postName);

    boolean insertComment(Comment comment);

    String getBarNameByPostName(String postName);
}
