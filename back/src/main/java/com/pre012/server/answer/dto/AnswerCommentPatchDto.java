package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerCommentPatchDto {
    private Long memberId;
    private Long commentId;
    private String content;
}
