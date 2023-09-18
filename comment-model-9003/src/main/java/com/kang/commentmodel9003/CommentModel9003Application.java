package com.kang.commentmodel9003;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.kang.commentmodel9003.mapper")
public class CommentModel9003Application {

    public static void main(String[] args) {
        SpringApplication.run(CommentModel9003Application.class, args);
    }

}
