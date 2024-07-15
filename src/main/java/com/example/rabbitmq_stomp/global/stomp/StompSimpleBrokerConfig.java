package com.example.rabbitmq_stomp.global.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Profile("!prod")
@Configuration
@EnableWebSocketMessageBroker
public class StompSimpleBrokerConfig implements WebSocketMessageBrokerConfigurer {
    /*
    스톰프(웹소켓)를 사용하기 위한 메세지 브로커 설정(필수)
    */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        /*
        클라이언트가 웹소켓 연결을 수립하기 위해 사용할 엔드포인트.
        클라이언트는 이 경로를 통해 서버와의 웹소켓을 연결
        SockJS를 사용하여 웹소켓을 지원하지 않는 브라우저에서도 대체 전송방식을 사용할 수 있도록 함.
         */
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        /*
        메모리 내에서 동작하는 간단한 메시지 브로커 활성화. 브로커는 클라이언트에게 메세지 전달.
        "/topic"접두사를 가진 모든 목적지에 대한 메세지를 브로커가 처리
         */
        config.enableSimpleBroker("/topic");
        /*
        어플리케이션에서 처리할 메세지의 목적지 접두사를 설정.
        클라이언트가 "/app"접두사를 가진 목적지에 대한 메세지를 전송하면, 그 메세지는 어플리케이션의 메시지 핸들러 메소드에 의해 처리.
         */
        config.setApplicationDestinationPrefixes("/app");
    }

}
