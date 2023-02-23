package com.pre012.server.member.controller;

import javax.validation.Valid;

import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.mapper.MemberMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import com.pre012.server.member.service.MemberService;


@RestController
@RequestMapping("/members")
public class MemberController {
    
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        Member member = memberMapper.signUpDtoToMember(signUpDto);
        memberService.createMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/profile/{member-id}")
    public ResponseEntity<SingleResponseDto<ProfileResponseDto>> getMemberProfile(
            @PathVariable("member-id") Long memberId)
    {
        Member member = memberService.getMemberProfile(memberId);
        ProfileResponseDto response = memberMapper.memberToProfileResponseDto(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity getMemberQuestions() {
        return null;
    }

    @GetMapping("/answers")
    public ResponseEntity getMemberAnswers() {
        return null;
    }

    @GetMapping("/bookmark")
    public ResponseEntity getMemberBookmarks() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchMember() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteMember() {
        return null;
    }
}
