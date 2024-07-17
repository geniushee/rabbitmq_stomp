package com.example.rabbitmq_stomp.domain.member.member.entity;

import com.example.rabbitmq_stomp.global.entity.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    private String username;
    private String password;
    private String refreshToken;

    public List<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public List<String> getAuthoritiesAsStringList(){
        List<String> list = new ArrayList<>();
        list.add("ROLE_USER");
        if(username.equals("admin")) list.add("ROLE_ADMIN");
        return list;
    }
}
