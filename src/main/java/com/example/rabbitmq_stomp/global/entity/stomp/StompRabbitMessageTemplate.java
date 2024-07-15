package com.example.rabbitmq_stomp.global.entity.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class StompRabbitMessageTemplate implements StompMessageTemplate {
    private final RabbitTemplate template;

    @Override
    public void convertAndSend(String exchange, String routingKey, Object object) {
        template.convertAndSend(exchange, routingKey, object);
    }
}
