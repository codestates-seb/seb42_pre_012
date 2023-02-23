package com.pre012.server.answer.dto;

import com.pre012.server.common.audit.Auditable;
import lombok.Getter;

@Getter
public class AnswerResponseDto extends Auditable {
    private Long member_id;
    private Long question_id;
    private Long answer_id;
    private String content;
    private int likes;

    public AnswerResponseDto(Long member_id, Long question_id, Long answer_id, String content, int likes) {
        this.member_id = member_id;
        this.question_id = question_id;
        this.answer_id = answer_id;
        this.content = content;
        this.likes = likes;
    }
}
