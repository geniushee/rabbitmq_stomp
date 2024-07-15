package com.example.rabbitmq_stomp.global.entity.stomp;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompRabbitMqBrokerConfig implements WebSocketMessageBrokerConfigurer {
    /*
    Rabbitmq 브로커 설정
     */

    /*
    simpleBrokerConfig와 동일
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws").withSockJS();
    }

    /*
    rabbitmq 관련된 설정 추가
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry
                .setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/topic")
                .setRelayHost("localhost") //rabbitmq 호스트
                .setRelayPort(61613) //rabbitmq 포트
                .setClientLogin("admin") // Login username
                .setClientPasscode("admin") // login password
                .setSystemLogin("admin")
                .setSystemPasscode("admin");
    }

    /*
    MessageConverter 추가
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
