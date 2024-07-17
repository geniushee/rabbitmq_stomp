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

    @Getter
    private static String cookieDomain;

    @Value("${custom.prod.cookieDomain}")
    public void setCookieDomain(String domain){
        cookieDomain = domain;
    }

    @Getter
    private static String frontUrl;

    @Value("${custom.prod.frontUrl}")
    public void setFrontUrl(String url){

    }
}
