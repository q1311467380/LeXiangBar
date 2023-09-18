package com.kang.commentmodel9003;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PublishCommentTest {
    @Autowired
    CommentMapper commentMapper;
    @Test
    public void insertTest(){
        Comment comment = new Comment("发帖单元测试","newPost2","kang");
        boolean insertComment = commentMapper.insertComment(comment);
        log.info("插入：" + insertComment);
    }
}
