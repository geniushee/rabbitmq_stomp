package com.example.rabbitmq_stomp.domain.member.member.service;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.global.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AuthService {

    public Cookie[] createTokens(Member member) {
        Map<String, Object> data = Map.of(
                "id", member.getId(),
                "username",member.getUsername(),
                "authorities", member.getAuthoritiesAsStringList());
        Cookie accessToken = JwtUtil.createCookie("accessToken", data);
        Cookie refreshToken = JwtUtil.createCookie("refreshToken");

        return new Cookie[]{accessToken, refreshToken};
    }
}
