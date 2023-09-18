package com.kang.barmodel9001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.kang.barmodel9001.mapper")
public class BarModel9001Application {

    public static void main(String[] args) {
        SpringApplication.run(BarModel9001Application.class, args);
    }

}
