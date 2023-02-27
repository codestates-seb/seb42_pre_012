package com.pre012.server.question.dto;

import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

public class QuestionDto {

    /**
     * post, patch DTO
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        @Positive
        private Long memberId;

        @NotBlank
        // 질문 앞 뒤에 공백있을 때, 단어 사이에 연속 공백 2개 이상일 때 유효성 검증 실패
        // test title(O), test  title(X), 1. test (O), test1- title(O), ...
        //  특수문자로 시작해도 괜찮나요? @@@@@@@@@@@@@@@
        @Pattern(regexp = "^(\\S)+(\\s?\\S)*$",
                message = "질문 제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank(message = "질문 내용은 공백이 아니어야 합니다.")
        private String content;

        @Valid
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
