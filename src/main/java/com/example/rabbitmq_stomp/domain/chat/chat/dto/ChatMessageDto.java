package com.example.rabbitmq_stomp.domain.chat.chat.dto;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long id;
    private Long roomId;
    private String writerName;
    private String body;

    public ChatMessageDto(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.roomId = chatMessage.getChatRoom().getId();
        this.writerName = chatMessage.getWriter().getUsername();
        this.body = chatMessage.getBody();

    }
}
