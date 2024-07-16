package com.example.rabbitmq_stomp.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Getter
    private static String jwtSecretKey;

    @Value("${jwt.secretKey}")
    public void setJwtSecretKey(String key){
        jwtSecretKey = key;
    }
}
