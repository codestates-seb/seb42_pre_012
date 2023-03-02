package com.pre012.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class MemberInfoDto {
    // -- 최종 Response Dto

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberAnswersResponseDto {

        private MemberDto.MemberSimpleInfo member;
        private List<QuestionResponse> questions;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberQuestionsResponseDto {
        private MemberDto.MemberSimpleInfo member;
        private List<QuestionResponse> questions;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberBookmarksResponseDto {
        private List<QuestionResponse> questions;
    }
    // -- 최종 ResponseDto 구성요소

    @Getter
    @Setter
    @AllArgsConstructor
    public static class QuestionResponse {
        private Long questionId;
        private String title;
        private String content;
        private String imagePath;
        private int viewCnt;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
        private int answerCnt;
        private WriterResponse writer;
        private List<TagResponse> tags;
        private List<AnswerResponse> answers;
    }

    @Getter
    @AllArgsConstructor
    public static class WriterResponse {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImage;
    }

    @Getter
    @AllArgsConstructor
    public static class TagResponse {
        private Long tagId;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class MyQuestionResponse {
        private Long questionId;
        private String title;
        private String content;
        private String imagePath;
        private int viewCnt;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
        private List<AnswerResponse> answers;
        private List<TagResponse> tags;
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerResponse {
        private Long answerId;
        private String content;
        private String imagePath;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
    }

}
