package com.pre012.server.question.dto;

import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class QuestionDto {

    /**
     * post, patch DTO
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        private Long memberId;
        private String title;
        private String content;
        private List<TagDto.Request> tags;
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
        private String createdAt;
        private String modifiedAt;
    }

    /**
     * 질문 목록 조회 및 필터링 & 질문 검색 결과 DTO
     */

    @Getter
    @AllArgsConstructor
    public static class searchResponse {
        private Long questionId;
        private String title;
        private String content;
        private String imagePath;
        private int viewCnt;
        private int likeCnt;
        private String createdAt;
        private String modifiedAt;
        private int answerCnt;
        private MemberInfoDto.WriterResponse member;
        private List<TagDto.Response> tags;
    }

    @Getter
    @AllArgsConstructor
    public static class resultResponse {
        private List<searchResponse> questions;
    }

    /**
     * 질문 상세 조회 결과 DTO
     */
    @Getter
    @AllArgsConstructor
    public static class getResponse {
        private Response question;
        private List<TagDto.Response> tags;
        private List<QuestionCommentDto.Response> comments;
        private MemberInfoDto.WriterResponse member;
        private Boolean isBookmarked;
        private LikeType likeStatus;
    }

}
