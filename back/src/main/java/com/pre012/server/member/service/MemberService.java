package com.pre012.server.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pre012.server.member.entity.Member;
import com.pre012.server.member.repository.MemberRepository;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(Member member) {

    }

    public void verifyMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (!member.isPresent()) throw new RuntimeException(); // 예외처리 나중에 바꾸겠습니다 (23.02.22)
    }

    public Member findVerifiedMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new RuntimeException());
        return findMember;
    }

    public void verifyAlreadyExistsEmailOrDisplayName(String email, String displayName) {
        Member member = memberRepository.findByEmailOrDisplayName(email, displayName).orElse(null);

        if (member != null && member.getEmail().equals(email))
            throw new RuntimeException();
        else if (member != null && member.getDisplayName().equals(displayName))
            throw new RuntimeException();
    }

    // private void encryptPassword(Member member) {
    //     String encryptedPassword = passwordEncoder.encode(member.getPassword());
    //     member.setPassword(encryptedPassword);
    // }

}
