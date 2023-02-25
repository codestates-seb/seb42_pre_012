package com.pre012.server.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberInfoDto {
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
    public static class WriterResponse {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
    }
}
