package com.example.rabbitmq_stomp.domain.member.member.controller;

import com.example.rabbitmq_stomp.domain.member.member.dto.MemberLoginRequestDto;
import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "domain/member/member/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberLoginRequestDto dto,
                        HttpServletResponse response){
        Member member = memberService.checkUsernameAndPassword(dto.getUsername(), dto.getPassword());

        Cookie[] cookies = memberService.createTokens(response, member);

        for(Cookie ck : cookies){
            response.addCookie(ck);
        }

        return "redirect:/";
    }
}
