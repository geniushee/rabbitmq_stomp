package com.example.rabbitmq_stomp.global.jwt;

import com.example.rabbitmq_stomp.global.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> opAccessToken = Optional.empty();

        if (cookies != null) {
            opAccessToken = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken")).findFirst();
        }

        if (opAccessToken.isPresent()) {
            Cookie accessToken = opAccessToken.get();

            // todo 리프레시 토큰으로 갱싱하는 프로세스 필요.
            if (JwtUtil.validateToken(accessToken.getValue())) {
                Map<String, Object> data = JwtUtil.getDataFrom(accessToken.getValue());
                // 데이터베이스를 뒤지지 않도록 data에 username, id, authority추가
                Long id = Long.valueOf((Integer) data.get("id"));
                String username = (String) data.get("username");
                List<String> list = (List<String>) data.get("authorities");
                List<GrantedAuthority> authorities = list.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                SecurityUser securityUser = new SecurityUser(
                        id,
                        username,
                        "",
                        authorities);
                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(securityUser, "", authorities)
                        );
            }

        }

        filterChain.doFilter(request, response);
    }
}
