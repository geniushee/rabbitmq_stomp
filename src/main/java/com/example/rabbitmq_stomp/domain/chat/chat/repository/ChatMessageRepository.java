package com.example.rabbitmq_stomp.domain.chat.chat.repository;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatRoomId(Long roomId);
}
