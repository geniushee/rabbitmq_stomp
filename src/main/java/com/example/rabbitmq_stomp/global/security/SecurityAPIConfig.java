package com.example.rabbitmq_stomp.global.security;

import com.example.rabbitmq_stomp.global.jwt.JwtFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityAPIConfig {
    private final JwtFilterChain jwtFilterChain;
    /*
    필터 체인 적용: securityMatcher()는 특정 경로에 대해 전체 보안 필터 체인을 적용할지 여부를 결정하는 데 사용됩니다. 예를 들어, /api/** 경로와 /admin/** 경로에 대해 서로 다른 보안 설정을 적용하려면 두 개의 필터 체인을 각각 설정할 수 있습니다.
인가 규칙 설정: authorizeHttpRequests()는 필터 체인 내에서 특정 요청 경로에 대한 인가 규칙을 정의합니다. 이는 보안 필터 체인이 적용된 이후에 요청이 어떻게 처리될지를 결정하는 데 사용됩니다.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
