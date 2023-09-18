package com.kang.usermodel9000;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.kang.usermodel9000.mapper")
public class UserModel9000Application {

    public static void main(String[] args) {
        SpringApplication.run(UserModel9000Application.class, args);
    }

}
