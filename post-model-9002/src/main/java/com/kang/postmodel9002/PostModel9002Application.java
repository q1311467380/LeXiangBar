package com.kang.postmodel9002;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.kang.postmodel9002.mapper")
@EnableScheduling
public class PostModel9002Application {

    public static void main(String[] args) {
        SpringApplication.run(PostModel9002Application.class, args);
    }

}
