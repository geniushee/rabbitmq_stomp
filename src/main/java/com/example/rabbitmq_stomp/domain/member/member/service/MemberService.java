package com.example.rabbitmq_stomp.domain.member.member.service;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.repository.MemberRepository;
import com.example.rabbitmq_stomp.global.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member checkUsernameAndPassword(String username, String rawPassword) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("없는 사용자"));

        if(!passwordEncoder.matches(rawPassword, member.getPassword())){
            throw new RuntimeException("비밀번호 틀림");
        }

        return member;
    }

    public Cookie[] createTokens(HttpServletResponse response, Member member) {
        Map<Object, Object> data = Map.of("username",member.getUsername());
        Cookie accessToken = JwtUtil.createCookie("accessToken", data);
        Cookie refreshToken = JwtUtil.createCookie("refreshToken");

        return new Cookie[]{accessToken, refreshToken};
    }
}
