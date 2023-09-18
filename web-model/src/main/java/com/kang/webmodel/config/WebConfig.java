package com.kang.webmodel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * cors远程服务的解决
     * 从前端页面发送的远程调用，前端页面无法使用restTemplate
     * @param registry
     */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true)//表示允许发送身份验证凭证（如Cookie）
                    .maxAge(3600);//预检请求的缓存时间1小时
        }

    /**
     * 虚拟路径
     * 解决服务器无法访问本地文件的问题
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/kang/**")
                .addResourceLocations("file:/C:/Users/86157/Desktop/post/");
    }
}

