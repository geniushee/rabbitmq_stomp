package com.example.rabbitmq_stomp.domain.chat.chat.dto;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.member.member.dto.MemberDto;
import lombok.Data;

@Data
public class ChatRoomDto {
    private Long roomId;
    private MemberDto creater;
    private String roomName;

    public ChatRoomDto(ChatRoom chatRoom){
        this.roomId = chatRoom.getId();
        this.creater = new MemberDto(chatRoom.getCreater());
        this.roomName = chatRoom.getRoomName();
    }
}
