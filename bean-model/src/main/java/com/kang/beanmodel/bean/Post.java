package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 帖子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer id;
    private String postName;
    private Integer viewNumber;
    private Integer userId;
    private Integer barId;
    private String content;
    private String username;
    private String barName;
    private String filePath;
    private String contentType;
    private Timestamp createTime;

    public Post(String postName, String username, String barName, String content, String filePath, Timestamp createTime) {
        this.postName = postName;
        this.content = content;
        this.username = username;
        this.barName = barName;
        this.filePath = filePath;
        this.createTime = createTime;
    }
}
