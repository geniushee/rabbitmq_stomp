package com.example.rabbitmq_stomp.domain.member.member.controller;

import com.example.rabbitmq_stomp.domain.member.member.dto.MemberDto;
import com.example.rabbitmq_stomp.domain.member.member.dto.MemberLoginRequestDto;
import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.AuthService;
import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto dto,
                                   HttpServletResponse response){
        Member member = memberService.checkUsernameAndPassword(dto.getUsername(), dto.getPassword());
        Cookie[] cookies = authService.createTokens(member);

        for(Cookie ck : cookies){
            response.addCookie(ck);
        }

        return ResponseEntity.ok(new MemberDto(member));
    }
}
