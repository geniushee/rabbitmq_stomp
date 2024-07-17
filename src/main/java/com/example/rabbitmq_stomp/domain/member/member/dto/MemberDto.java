package com.example.rabbitmq_stomp.domain.member.member.dto;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String username;

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
