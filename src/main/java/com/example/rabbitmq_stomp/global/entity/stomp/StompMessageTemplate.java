package com.example.rabbitmq_stomp.global.entity.stomp;

public interface StompMessageTemplate {
    void convertAndSend(String exchange, String routingKey, Object object);
}
