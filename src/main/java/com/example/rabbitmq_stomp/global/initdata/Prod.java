package com.example.rabbitmq_stomp.global.initdata;

import com.example.rabbitmq_stomp.domain.chat.chat.entity.ChatRoom.ChatRoom;
import com.example.rabbitmq_stomp.domain.chat.chat.service.ChatService;
import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Profile("prod")
@Configuration
@RequiredArgsConstructor
public class Prod {

    @Autowired
    @Lazy
    private Prod self;

    private final ChatService chatService;
    private final MemberService memberService;


    @Bean
    public ApplicationRunner initData(){
        return args -> {
          self.work1();
        };
    }

    public void work1() {
        System.out.println("운영모드");
        ChatRoom room1 = chatService.createRoom("room1");
        ChatRoom room2 = chatService.createRoom("room2");
        ChatRoom room3 = chatService.createRoom("room3");

        Member member1 = memberService.register("user1", "1234");
        Member member2 = memberService.register("user2", "1234");
        Member member3 = memberService.register("user3", "1234");

        Optional<Member> opMember = memberService.findByUsername("user1");
        if(opMember.isEmpty()){
            System.out.println("왜 비었냐?");
        }

        chatService.writeMessage(room1,member1.getUsername(), "message1");
        chatService.writeMessage(room1,member1.getUsername(), "message2");
        chatService.writeMessage(room1, member1.getUsername(), "message3");

        chatService.writeMessage(room1, member2.getUsername(), "message4");
        chatService.writeMessage(room1, member2.getUsername(), "message5");

        chatService.writeMessage(room1, member3.getUsername(), "message6");

        chatService.writeMessage(room2, member1.getUsername(), "message7");
        chatService.writeMessage(room2, member2.getUsername(), "message8");

        chatService.writeMessage(room3, member1.getUsername(), "message9");
    }
}
