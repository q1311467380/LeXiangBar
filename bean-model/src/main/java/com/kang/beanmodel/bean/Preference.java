package com.kang.beanmodel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 偏好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    private Integer id;
        private String username;
    /**
     * 贴吧的类型标识
     * 使用该标识查询简单
     * 但是插入时该标识非空，一定要插入
     */
    private String kindName;
}
