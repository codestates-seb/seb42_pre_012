package com.pre012.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class QuestionCommentDto {

    /**
     * post, patch DTO
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        @Positive
        private Long memberId;

        @NotBlank(message = "댓글 내용은 공백이 아니어야 합니다.")
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
