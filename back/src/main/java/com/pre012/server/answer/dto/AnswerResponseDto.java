package com.pre012.server.answer.dto;

import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.common.audit.Auditable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AnswerResponseDto {
    //private Long memberId;
    //private Long questionId;
    private Long answerId;
    private String content;
    private int likeCnt;
    private String imagePath;
    private String createdAt;
    private String modifiedAt;

    public AnswerResponseDto(Long answerId, String content, int likeCnt,String imagePath, String createdAt, String modifiedAt) {
//        this.memberId = memberId;
//        this.questionId = questionId;
        this.answerId = answerId;
        this.content = content;
        this.likeCnt = likeCnt;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
