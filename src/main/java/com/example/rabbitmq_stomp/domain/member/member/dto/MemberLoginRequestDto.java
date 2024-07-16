package com.example.rabbitmq_stomp.domain.member.member.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String username;
    private String password;
}
