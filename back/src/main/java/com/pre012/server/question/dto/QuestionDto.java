package com.pre012.server.question.dto;

import com.pre012.server.question.entity.QuestionTag;
import com.pre012.server.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private Long memberId;
        private String title;
        private String content;
        private List<TagDto> tags; // 수정 필요
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long memberId;
        private String title;
        private String content;
        private List<QuestionTag> tag; // DTO로 받기 수정 필요

        private Long questionId;

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long questionId;
        private String title;
        private String content;
        private int viewCnt;
        private int likeCnt;
        private String imagePath;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Long memberId;
    }

    @Getter
    @AllArgsConstructor
    public static class questionListResponse {
        private Long questionId;
        private String title;
        private String content;
        private int viewCnt;
        private int likeCnt;
        private String imagePath;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Long memberId;
        private int answerCnt;
    }

}
