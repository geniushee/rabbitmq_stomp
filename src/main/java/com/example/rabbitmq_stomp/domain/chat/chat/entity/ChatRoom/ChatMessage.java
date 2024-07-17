package com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.global.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatMessage extends BaseTime {
    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private Member writer;
    private String body;

}
