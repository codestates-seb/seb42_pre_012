package com.pre012.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.common.dto.MultiObjectResponseDto;
import com.pre012.server.member.dto.MemberDto.SignUpDto;
import com.pre012.server.member.dto.MemberDto.ModifyDto;
import com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import com.pre012.server.member.dto.MemberDto.MemberSimpleInfo;
import com.pre012.server.member.dto.MemberInfoDto.MemberAnswersResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberQuestionsResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberBookmarksResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.mapper.MemberMapper;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {
    
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        Member member = memberMapper.signUpDtoToMember(signUpDto);
        memberService.createMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 회원 정보_프로필
    @GetMapping("/profile/{member-id}")
    public ResponseEntity<SingleResponseDto<ProfileResponseDto>> getMemberProfile(
            @PathVariable("member-id") @Positive Long memberId)
    {
        Member member = memberService.getMemberProfile(memberId);
        ProfileResponseDto response = memberMapper.memberToProfileResponseDto(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 회원 정보_내 질문
    @GetMapping("/questions/{member-id}")
    public ResponseEntity<MultiObjectResponseDto<MemberQuestionsResponseDto, Question>> getMemberQuestions(
            @PathVariable("member-id") @Positive Long memberId,
            @RequestParam("page") @Positive int page)
    {
        MemberSimpleInfo member = memberMapper.memberToMemberSimpleInfoDto(
                memberService.getMemberProfile(memberId));

        Page<Question> questions = memberService.getMemberQuestions(memberId, page);
        MemberQuestionsResponseDto response = memberMapper.questionsToMemberQuestionsDto(questions.getContent());
        response.setMember(member);

        return new ResponseEntity<>(new MultiObjectResponseDto<>(response, questions), HttpStatus.OK);
    }

    // 회원 정보_내 답변
    @GetMapping("/answers/{member-id}")
    public ResponseEntity<MultiObjectResponseDto<MemberAnswersResponseDto, Question>> getMemberAnswers(
            @PathVariable("member-id") @Positive Long memberId,
            @RequestParam("page") @Positive int page)
    {
        MemberSimpleInfo member = memberMapper.memberToMemberSimpleInfoDto(
                memberService.getMemberProfile(memberId));

        Page<Question> questionsMemberAnswered
                = memberService.getMemberAnsweredQuestions(memberId, page);
        MemberAnswersResponseDto response
                = memberMapper.questionsToMemberAnswersDto(questionsMemberAnswered.getContent());
        response.setMember(member);

        return new ResponseEntity<>(
                new MultiObjectResponseDto<>(response, questionsMemberAnswered), HttpStatus.OK);
    }

    // 회원 정보_내 북마크
    @GetMapping("/bookmark/{member-id}")
    public ResponseEntity<MultiObjectResponseDto<MemberBookmarksResponseDto, Question>> getMemberBookmarks(
            @PathVariable("member-id") @Positive Long memberId,
            @RequestParam("page") @Positive int page)
    {
        Page<Question> bookmarks = memberService.getMemberBookmarks(memberId, page);
        MemberBookmarksResponseDto response = memberMapper.memberToMemberBookmarksDto(bookmarks.getContent());
        return new ResponseEntity<>(new MultiObjectResponseDto<>(response, bookmarks), HttpStatus.OK);
    }

    // 회원 정보 수정
    @PatchMapping("/{member-id}")
    public ResponseEntity<HttpStatus> patchMember(@PathVariable("member-id") @Positive Long memberId,
                                                  @RequestBody ModifyDto modifyDto)
    {
        Member member = memberMapper.modifyDtoToMember(modifyDto);
        member.setId(memberId);
        memberService.updateMember(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/{member-id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("member-id") @Positive Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
