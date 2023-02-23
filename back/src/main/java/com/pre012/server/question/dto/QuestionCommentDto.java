package com.pre012.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

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
     * 작성자 정보는 필요 없나요?
     */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long commentId;
        private String content;
        private String commentWriter;
        private Date createdAt;
    }

}
