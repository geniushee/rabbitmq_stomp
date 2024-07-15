package com.example.rabbitmq_stomp.global.stomp;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Profile("prod")
@Configuration
@EnableWebSocketMessageBroker
public class StompRabbitMqBrokerConfig implements WebSocketMessageBrokerConfigurer {
    /*
    Rabbitmq 브로커 설정
     */
    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;
    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;


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
                .setRelayHost(rabbitmqHost) //rabbitmq 호스트
                .setRelayPort(rabbitmqPort) //rabbitmq 포트
                .setClientLogin(rabbitmqUsername) // Login username
                .setClientPasscode(rabbitmqPassword) // login password
                .setSystemLogin(rabbitmqUsername)
                .setSystemPasscode(rabbitmqPassword);
    }

    /*
    MessageConverter 추가
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
