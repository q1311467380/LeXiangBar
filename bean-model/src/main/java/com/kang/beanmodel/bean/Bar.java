package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 贴吧
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bar {
    private Integer id;
    private String barName;
    private String kindName;
    private Integer postNumber;
    private Integer fansNumber;
    private List<Post> postsList;
    private String username;
    private Integer userId;
    private String description;
}
