package com.pre012.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class QuestionCommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private Long memberId;
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long memberId;
        private Long commentId;
        private String content;

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
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
