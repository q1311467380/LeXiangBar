package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String content;
    private String postName;
    private String username;
    private Timestamp commentTime;

    public Comment(String content, String postName, String username) {
        this.content = content;
        this.postName = postName;
        this.username = username;
    }
}
