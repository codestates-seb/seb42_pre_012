package com.pre012.server.question.dto;

import com.pre012.server.member.dto.MemberDto;
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
//        private List<TagDto> tags; // 수정 필요
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long memberId;
        private String title;
        private String content;
//        private List<QuestionTag> tag; // DTO로 받기 수정 필요

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
        private String imagePath;
        private int viewCnt;
        private int likeCnt;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    /**
     * 질문 목록 조회 및 필터링 & 질문 검색 결과 DTO
     * @tags 부분 수정 필요
     */

    @Getter
    @AllArgsConstructor
    public static class searchResponse {
        private Response questions;
        private MemberDto.SimpleInfo member;
        private int answerCnt;
//        private TagDto.Response tags;
    }

    /**
     * 질문 상세 조회 결과 DTO
     * @tags 수정 필요
     */
    @Getter
    @AllArgsConstructor
    public static class getResponse {
        private Response question;
        private List<QuestionCommentDto.Response> comments;
        private MemberDto.SimpleInfo member;
        private boolean isBookmarked;
        private String likeStatus;
//        private TagDto.Response tags;
    }

}
