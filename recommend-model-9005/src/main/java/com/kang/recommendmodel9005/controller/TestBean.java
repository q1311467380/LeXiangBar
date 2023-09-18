package com.kang.recommendmodel9005.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 页面回显尝试，返回的数据对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBean {
    public String testBarName;
    public String path;
}
