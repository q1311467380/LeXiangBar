package com.kang.commentmodel9003.controller;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.mapper.CommentMapper;
import com.kang.commentmodel9003.service.GetCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 获取指定帖子的评论
 */
@Controller
public class GetCommentController {
    @Autowired
    GetCommentService getCommentService;
    @RequestMapping("/getPostComment")
    @ResponseBody
    @CrossOrigin
    public List<Comment> getPostComment(@RequestParam String postName){
        return getCommentService.getAllCommentByPostName(postName);
    }
}
