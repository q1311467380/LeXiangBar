package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 粉丝
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fans {
    private Integer id;
    private Integer barId;
    private Integer userId;
    private Timestamp attentionTime;
    private String barName;
    private String username;

    public Fans(String username, String barName, Timestamp attentionTime) {
        this.username = username;
        this.barName = barName;
        this.attentionTime = attentionTime;
    }
}
