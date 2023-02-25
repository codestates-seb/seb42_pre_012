package com.pre012.server.answer.dto;

import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.common.audit.Auditable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AnswerResponseDto {
    private Long member_id;
    private Long question_id;
    private Long answer_id;
    private String content;
    private int likeCnt;
    private String image_path;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AnswerResponseDto(Long member_id, Long question_id, Long answer_id, String content, int likeCnt,String image_path, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.member_id = member_id;
        this.question_id = question_id;
        this.answer_id = answer_id;
        this.content = content;
        this.likeCnt = likeCnt;
        this.image_path = image_path;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
