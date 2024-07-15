package com.example.rabbitmq_stomp.domain.chat.chat.controller;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.chat.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate template;

    @GetMapping("/{roomId}")
    public String showRoom(@PathVariable(name = "roomId")Long roomId,
                           Model model){
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        model.addAttribute("chatRoom", chatRoom);

        return "domain/chat/chat/room";
    }

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
        template.convertAndSend("/topic/chat" + roomId + "MessageCreated", chatMessage);
    }
}
