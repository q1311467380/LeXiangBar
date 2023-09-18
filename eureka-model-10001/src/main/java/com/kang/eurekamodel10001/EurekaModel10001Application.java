package com.kang.eurekamodel10001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaModel10001Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaModel10001Application.class, args);
    }

}
