package com.pre012.server.member.service;

import java.util.List;
import java.util.Optional;

import com.pre012.server.auth.util.CustomAuthorityUtils;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pre012.server.member.entity.Member;
import com.pre012.server.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository,
                         QuestionRepository questionRepository,
                         PasswordEncoder passwordEncoder,
                         CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public void createMember(Member member) {
        verifyAlreadyExistsEmailOrDisplayName(member.getEmail(), member.getDisplayName());
        encryptPassword(member);
        assignRole(member);
        memberRepository.save(member);
    }

    public Member getMemberProfile(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Question> getMemberQuestions(Long memberId, int page) {
        // 리팩토링 예정
        PageRequest pageable = PageRequest.of(page - 1, 15, Sort.Direction.DESC, "createdAt");
        return questionRepository.findByMemberId(memberId, pageable);
    }

    public Member getMemberAnswers(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Question> getMemberBookmarks(Long memberId, int page) {
        // 리팩토링 예정
        PageRequest pageable = PageRequest.of(page - 1, 15, Sort.Direction.DESC, "createdAt");
        return questionRepository.findByBookmarksMemberId(memberId, pageable);
    }
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = findVerifiedMember(memberId);
        memberRepository.delete(member);
    }

    public void verifyMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) throw new RuntimeException(); // 예외처리 나중에 바꾸겠습니다 (23.02.22)
    }

    public Member findVerifiedMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElseThrow(RuntimeException::new);
    }

    public void verifyAlreadyExistsEmailOrDisplayName(String email, String displayName) {
        Member member = memberRepository.findByEmailOrDisplayName(email, displayName).orElse(null);

        if (member != null && member.getEmail().equals(email))
            throw new RuntimeException();
        else if (member != null && member.getDisplayName().equals(displayName))
            throw new RuntimeException();
    }

    private void encryptPassword(Member member) {
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
    }

    private void assignRole(Member member) {
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
    }

}
