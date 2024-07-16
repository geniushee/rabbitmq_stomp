package com.example.rabbitmq_stomp.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers ->{
                    headers.frameOptions(
                            HeadersConfigurer.FrameOptionsConfig::sameOrigin
                    );
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests(authorization -> {
                    authorization
                            .requestMatchers("/h2-console/**")
                            .permitAll();
                    authorization
                            .requestMatchers("/**")
                            .permitAll();
                })
//                .sessionManagement(AbstractHttpConfigurer::disable) // 세션 미사용
//                .addFilterBefore(new JwtFilterChain(), UsernamePasswordAuthenticationFilter.class)
        ;


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
