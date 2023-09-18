package com.kang.postmodel9002.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RabbitConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("kang");
        cachingConnectionFactory.setPassword("0524");
        cachingConnectionFactory.setHost("localhost");
        return cachingConnectionFactory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(this.connectionFactory());
    }

}
