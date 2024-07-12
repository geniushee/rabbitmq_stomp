package com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom;

import com.example.rabbitmq_stomp.global.entity.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTime {
    private String name;
}
