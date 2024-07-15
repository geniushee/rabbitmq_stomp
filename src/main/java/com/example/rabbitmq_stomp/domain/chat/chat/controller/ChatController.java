package com.example.rabbitmq_stomp.domain.chat.chat.controller;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.chat.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final RabbitTemplate template;

    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable(name = "roomId")Long roomId,
                           Model model){
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        model.addAttribute("chatRoom", chatRoom);

        return "domain/chat/chat/room";
    }

    /**
     * repository에서 해당 채팅방의 채팅 내역을 가져온다.
     * @param roomId 채팅방 번호
     * @param model thymeleaf 모델
     * @return 채팅 내역 전체
     */
    @GetMapping("/{roomId}/messages")
    @ResponseBody
    public List<ChatMessage> showRoomMessages(@PathVariable(name = "roomId")Long roomId,
                                              Model model){
        List<ChatMessage> messages = chatService.findMessagesByRoomId(roomId);

        return messages;
    }

    public record CreateMessageReqBody(String writerName, String body){}

    @MessageMapping("/chat/{roomId}/messages/create")
    public void createMessage(
            CreateMessageReqBody createMessageReqBody,
            @DestinationVariable long roomId
    ){
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();

        ChatMessage chatMessage = chatService.writeMessage(chatRoom, createMessageReqBody.writerName(), createMessageReqBody.body());
        // 기본으로 생성되는 amq.topic exchange로 설정, routingkey 는 연결된 큐들 중 해당 패턴에 해당하는 큐로 연결
        template.convertAndSend("amq.topic", "chat" + roomId +"MessageCreated", chatMessage);
    }
}
