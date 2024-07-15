package com.example.rabbitmq_stomp.global.stomp;

public interface StompMessageTemplate {
    void convertAndSend(String exchange, String routingKey, Object object);
}
