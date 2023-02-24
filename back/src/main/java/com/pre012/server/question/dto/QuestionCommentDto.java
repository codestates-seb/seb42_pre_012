package com.pre012.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class QuestionCommentDto {

    /**
     * post, patch DTO
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        private Long memberId;
        private String content;

    }

    /**
     * 질문 상세 조회에 보낼 comment Response DTO
     */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long commentId;
        private String content;
        private String commentWriter;
        private String createdAt;
    }

}
