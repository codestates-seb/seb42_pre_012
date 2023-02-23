package com.pre012.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class MemberAnswersDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswersResponseDto {
        private QuestionMember member;
        private List<QuestionDto> questions;
    }

    @Getter
    @AllArgsConstructor
    public static class QuestionMember {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
    }

    @Getter
    @AllArgsConstructor
    public static class QuestionDto {
        private Long questionId;
        private String title;
        private String content;
        private String imagePath;
        private int viewCnt;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
        private List<AnswerDto> answers;
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerDto {
        private Long answerId;
        private String content;
        private String imagePath;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
    }
}
