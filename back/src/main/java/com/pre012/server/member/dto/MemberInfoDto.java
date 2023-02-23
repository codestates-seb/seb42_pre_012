package com.pre012.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class MemberInfoDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberAnswersDto {

        private WriterResponse member;
        private List<QuestionResponse> questions;
        private List<TagResponse> TagResponse;
    }

    @Getter
    @AllArgsConstructor
    public static class WriterResponse {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
    }

    @Getter
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

    @Getter
    @AllArgsConstructor
    public static class TagResponse {
        private Long tagId;
        private String name;
    }

}
