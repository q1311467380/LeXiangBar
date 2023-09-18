package com.kang.recommendmodel9005;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.kang.recommendmodel9005.mapper")
public class RecommendModel9005Application {

    public static void main(String[] args) {
        SpringApplication.run(RecommendModel9005Application.class, args);
    }

}
