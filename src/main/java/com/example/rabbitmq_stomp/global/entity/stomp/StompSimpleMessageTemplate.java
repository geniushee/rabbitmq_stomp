package com.example.rabbitmq_stomp.global.entity.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class StompSimpleMessageTemplate implements StompMessageTemplate {
    private final SimpMessagingTemplate template;

    @Override
    public void convertAndSend(String exchange, String routingKey, Object object) {
        template.convertAndSend("/"+exchange+"/"+routingKey, object);
    }
}
