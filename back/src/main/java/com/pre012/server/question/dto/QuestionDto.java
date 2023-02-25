package com.pre012.server.question.dto;

import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.enums.LikeType;
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
//        private List<TagDto> tags; // 수정 필요
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
     *
     * @tags 부분 수정 필요
     */

    @Getter
    @AllArgsConstructor
    public static class searchResponse {
        private Response questions;
        private MemberInfoDto.WriterResponse member;
        private int answerCnt;
//        private TagDto.Response tags;
    }

    /**
     * 질문 상세 조회 결과 DTO
     *
     * @tags 수정 필요
     */
    @Getter
    @AllArgsConstructor
    public static class getResponse {
        private Response question;
        private List<QuestionCommentDto.Response> comments;
        private MemberInfoDto.WriterResponse member;
        private Boolean isBookmarked;
        private LikeType likeStatus;
//        private TagDto.Response tags;
    }

}
