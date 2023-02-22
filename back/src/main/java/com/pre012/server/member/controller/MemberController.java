package com.pre012.server.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.pre012.server.member.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
    
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity signup() {
        return null;
    }

    @GetMapping("/profile")
    public ResponseEntity getMemberProfile() {
        return null;
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
