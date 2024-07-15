package com.example.rabbitmq_stomp.domain.member.member.entity;

import com.example.rabbitmq_stomp.global.entity.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
        if(this.username.contains("admin")){
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return list;
    }
}
