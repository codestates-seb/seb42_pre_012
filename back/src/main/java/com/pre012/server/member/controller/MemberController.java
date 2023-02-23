package com.pre012.server.member.controller;

import javax.validation.Valid;

import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.common.dto.MultiObjectResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.mapper.MemberMapper;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;

import com.pre012.server.member.dto.MemberInfoDto.MemberAnswersResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberQuestionsResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberBookmarksResponseDto;

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

    @GetMapping("/questions/{member-id}")
    public ResponseEntity<MultiObjectResponseDto<MemberQuestionsResponseDto, Question>> getMemberQuestions(
            @PathVariable("member-id") Long memberId,
            @RequestParam("page") int page)
    {
        Page<Question> questions = memberService.getMemberQuestions(memberId, page);
        MemberQuestionsResponseDto response = memberMapper.memberToMemberAnswersDto(questions.getContent());
        return new ResponseEntity<>(new MultiObjectResponseDto<>(response, questions), HttpStatus.OK);
    }

    @GetMapping("/answers/{member-id}")
    public ResponseEntity<SingleResponseDto<MemberAnswersResponseDto>> getMemberAnswers(
            @PathVariable("member-id") Long memberId,
            @RequestParam("page") int page)
    {
        Member member = memberService.getMemberAnswers(memberId);
        MemberAnswersResponseDto response = memberMapper.memberToMemberAnswersDto(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/bookmark/{member-id}")
    public ResponseEntity<MultiObjectResponseDto<MemberBookmarksResponseDto, Question>> getMemberBookmarks(
            @PathVariable("member-id") Long memberId,
            @RequestParam("page") int page)
    {
        Page<Question> bookmarks = memberService.getMemberBookmarks(memberId, page);
        MemberBookmarksResponseDto response = memberMapper.memberToMemberBookmarksDto(bookmarks.getContent());
        return new ResponseEntity<>(new MultiObjectResponseDto<>(response, bookmarks), HttpStatus.OK);
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
