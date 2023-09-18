package com.kang.commentmodel9003.controller;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.service.PublishCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 发表评论
 * 单元测试通过
 * 返回值：0为失败，1为成功
 */
@Controller
@Slf4j
public class PublishCommentController {
    @Autowired
    PublishCommentService publishCommentService;
    @RequestMapping("/publishComment")
    @ResponseBody
    @CrossOrigin
    public String publishComment(@RequestBody Comment comment, HttpServletRequest req){
        log.info("comment:" + comment);
        return publishCommentService.publishComment(comment,req);
    }
}
