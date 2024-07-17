package com.example.rabbitmq_stomp.global.rq;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import com.example.rabbitmq_stomp.global.security.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component("Rq")
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final MemberService memberService;
    private SecurityUser user;
    private Member member;
    private Boolean isLogin;


    private SecurityUser getUser(){
        if(user != null) return user;

        user = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof SecurityUser)
                .map(authentication -> (SecurityUser) authentication.getPrincipal())
                .orElse(null);

        isLogin = user != null;
        return user;
    }

    public Member getMember(){
        if(!isLogin()) return null;

        if(member==null) member = memberService.findReferenceById(getUser().getId());

        return member;
    }

    public boolean isLogin(){
        if(isLogin == null){
            getUser();
        }
        return isLogin;
    }

    public void destroySecurityScontextAuth() {
        request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
    }
}
