package com.example.rabbitmq_stomp.domain.member.member.controller;

import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    /*
    RequestBody는 content-type에서 application/json으로 처리해야 받을 수 있다.
    현재 html을 그대로 사용하고 있으므로 RequestParam이나, ModelAttribute로 받아야한다.
     */
//    @PostMapping("/login")
//    public String login(@ModelAttribute MemberLoginRequestDto dto,
//                        HttpServletResponse response){
//        System.out.println("id / ps : "+dto.getUsername()+" / "+dto.getPassword());
//
//        Member member = memberService.checkUsernameAndPassword(dto.getUsername(), dto.getPassword());
//
//        Cookie[] cookies = memberService.createTokens(response, member);
//
//        for(Cookie ck : cookies){
//            response.addCookie(ck);
//        }
//
//        return "redirect:/";
//    }
}
