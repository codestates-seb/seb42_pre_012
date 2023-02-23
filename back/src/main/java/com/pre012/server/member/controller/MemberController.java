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

import com.pre012.server.member.dto.MemberInfoDto.MemberAnswersDto;

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

    @GetMapping("/answers/{member-id}")
    public ResponseEntity<SingleResponseDto<MemberAnswersDto>> getMemberAnswers(
            @PathVariable("member-id") Long memberId,
            @RequestParam("page") int page)
    {
        Member member = memberService.getMemberAnswers(memberId);
        MemberAnswersDto response = memberMapper.memberToMemberAnswersDto(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/bookmark")
    public ResponseEntity getMemberBookmarks() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchMember() {
        return null;
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
