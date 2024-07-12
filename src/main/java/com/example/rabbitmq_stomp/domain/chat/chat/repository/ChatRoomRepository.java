package com.example.rabbitmq_stomp.domain.chat.chat.repository;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
