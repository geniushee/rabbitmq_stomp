package com.example.rabbitmq_stomp.global.security;

import com.example.rabbitmq_stomp.global.config.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    /*
    CorsConfig를 할 수 있는 다른 방법
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://cdpn.io", AppConfig.getFrontUrl())
                .allowedHeaders("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
