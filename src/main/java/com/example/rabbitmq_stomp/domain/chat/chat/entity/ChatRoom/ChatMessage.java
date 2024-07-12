package com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom;

import com.example.rabbitmq_stomp.global.entity.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore //Json의 직렬화/역직렬화 무시
    private ChatRoom chatRoom;
    private String writerName;
    private String body;

}
