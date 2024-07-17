package com.example.rabbitmq_stomp.global.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {
    @Getter
    private Long id;

    public SecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.id = id;
    }

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
