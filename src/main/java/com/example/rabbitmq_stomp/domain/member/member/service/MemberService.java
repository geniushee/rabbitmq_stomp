package com.example.rabbitmq_stomp.domain.member.member.service;

import com.example.rabbitmq_stomp.domain.member.member.entity.Member;
import com.example.rabbitmq_stomp.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member checkUsernameAndPassword(String username, String rawPassword) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("없는 사용자"));

        if(!passwordEncoder.matches(rawPassword, member.getPassword())){
            throw new RuntimeException("비밀번호 틀림");
        }

        return member;
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member findReferenceById(Long id) {
        return memberRepository.getReferenceById(id);
    }
}
