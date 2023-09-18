package com.kang.webmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModelApplication.class, args);
    }

}
