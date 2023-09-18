package com.kang.recommendmodel9005.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拦截的是登录后的操作
 * 登录逻辑的这段代码因为uuid是和password拼的要解析而这些不用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {

}
