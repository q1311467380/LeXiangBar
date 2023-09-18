package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String bio;//个人邮箱
    private List<Preference> preferencesList;
    private List<Fans> attentionList;
    private List<Comment> commentsList;
    private List<Post> postsList;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
