package com.example.rabbitmq_stomp.domain.chat.chat.service;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.chat.chat.repository.ChatMessageRepository;
import com.example.rabbitmq_stomp.domain.chat.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatRoom> findRoomById(Long roomId) {
        return chatRepository.findById(roomId);
    }

    public List<ChatMessage> findMessagesByRoomId(Long roomId) {
        return chatMessageRepository.findAllByChatRoomId(roomId);
    }

    @Transactional
    public ChatRoom createRoom(String roomName) {
        return chatRepository.save(ChatRoom.builder()
                .name(roomName)
                .build());
    }

    public ChatMessage writeMessage(ChatRoom room1, String writerName, String body) {
        ChatMessage message = ChatMessage.builder()
                .chatRoom(room1)
                .writerName(writerName)
                .body(body)
                .build();
        return chatMessageRepository.save(message);
    }
}
