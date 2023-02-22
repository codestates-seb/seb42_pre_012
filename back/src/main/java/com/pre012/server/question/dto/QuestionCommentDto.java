package com.pre012.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
