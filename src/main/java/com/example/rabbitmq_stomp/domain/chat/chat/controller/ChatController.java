package com.example.rabbitmq_stomp.domain.chat.chat.controller;

import com.example.rabbitmq_stomp.domain.chat.chat.dto.ChatMessageDto;
import com.example.rabbitmq_stomp.domain.chat.chat.dto.ChatRoomDto;
import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatMessage;
import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.chat.chat.service.ChatService;
import com.example.rabbitmq_stomp.domain.member.member.dto.MemberDto;
import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import com.example.rabbitmq_stomp.global.rq.Rq;
import com.example.rabbitmq_stomp.global.stomp.StompMessageTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final StompMessageTemplate template;
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public String showRoom(@PathVariable(name = "roomId")Long roomId,
                           Model model){
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
        Optional<Member> opMember = memberService.findById(rq.getMember().getId());
        MemberDto memberDto = new MemberDto(opMember.get());
        model.addAttribute("chatRoom", chatRoomDto);
        model.addAttribute("member", memberDto);

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
    @Transactional(readOnly = true)
    public List<ChatMessageDto> showRoomMessages(@PathVariable(name = "roomId")Long roomId,
                                              Model model){
        List<ChatMessage> messages = chatService.findMessagesByRoomId(roomId);

        List<ChatMessageDto> messageDtos = messages.stream()
                .map(ChatMessageDto::new).toList();

        return messageDtos;
    }

    public record CreateMessageReqBody(Long memberId, String body){}

    // 메세징이라서 scope이 다르고 http를 사용하지 못한다.
    @MessageMapping("/chat/{roomId}/messages/create")
    @Transactional(readOnly = true)
    public void createMessage(
            CreateMessageReqBody createMessageReqBody,
            @DestinationVariable long roomId
    ){
        //todo 임시
        Member member1 = memberService.findByUsername("user1").get();
        System.out.println("채팅 생성자 아이디 : " + createMessageReqBody.memberId);
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();

        ChatMessage chatMessage = chatService.writeMessage(chatRoom, member1, createMessageReqBody.body());
        // 기본으로 생성되는 amq.topic exchange로 설정, routingkey 는 연결된 큐들 중 해당 패턴에 해당하는 큐로 연결
        template.convertAndSend("topic", "chat" + roomId +"MessageCreated",new ChatMessageDto(chatMessage));
    }

    @GetMapping("/roomList")
    public String getChatRoomList(Model model){
        List<ChatRoomDto> list = chatService.findAll();
        model.addAttribute("list", list);
        return "domain/chat/chat/roomList";
    }
}
