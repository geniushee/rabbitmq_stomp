package com.example.rabbitmq_stomp.global.security;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.AuthService;
import com.example.rabbitmq_stomp.global.rq.Rq;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 로그인 페이지 이동 전 페이지로 이동하는 handler인 SavedRequestAwareAuthenticationSuccessHandler를 상속받은 커스텀 핸들러이다.
 * <p>로그인 성공 후 쿠키를 전달하기 위해 해당 핸들러를 사용</p>
 * <p>핸들러를 도입하기 전에는 PostMapping을 통해서 로그인 요청을 직접 구현했다.</p>
 * <p>frontend 도입 후 api를 사용할 때도 유용하게 이용할 수 있다.</p>
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Rq rq;
    private final AuthService authService;

    /*
    처음에는 로그인 POST요청을 가로채는 방법을 사용하려고 했으나 매우 번거롭다.
    ---------------
    번거로운 과정
    1. UserPasswordAuthenticationFilter를 상속받는다.
    2. AuthenticationManager에 login 요청에 대응하는 Url을 새로게 설정한다.
    3. 로그인 시도시(attemptAuthentication method) Authentication 생성을 커스텀한다.
    4. 로그인 성공시(successfulAuthentication method) 절차를 커스텀한다.
    5. 로그인 실패시(unsuccessfulAuthentication method) 절차를 커스텀한다.
    6. 마지막으로 security filterchain에 생성한 커스텀 필터를 UserPasswordAuthenticationFilter보다 앞에 실행되도록 등록한다.
    ---------------
    이러한 과정은 귀찮으니 로그인은 UserPasswordAuthenticationFilter에 맡기고 성공한 이후에 쿠키를 설정하는 방법을 선택.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException{
        Member member = rq.getMember();

        Cookie[] cookies = authService.createTokens(member);

        for(Cookie ck : cookies){
            response.addCookie(ck);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
