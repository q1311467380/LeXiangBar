package com.kang.attentionmodel9004;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.kang.attentionmodel9004.mapper")
@EnableEurekaClient
public class AttentionModel9004Application {

    public static void main(String[] args) {
        SpringApplication.run(AttentionModel9004Application.class, args);
    }

}
