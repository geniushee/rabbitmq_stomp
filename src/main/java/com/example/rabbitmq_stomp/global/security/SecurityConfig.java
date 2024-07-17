package com.example.rabbitmq_stomp.global.security;

import com.example.rabbitmq_stomp.global.jwt.JwtFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilterChain jwtFilterChain;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers -> {
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
                // 세션 미사용
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .formLogin(formlogin ->
                        formlogin.loginPage("/member/login")
                                .permitAll()
                                .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                .deleteCookies("accessToken", "refreshToken"))
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
