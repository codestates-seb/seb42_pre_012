package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerCommentPatchDto {
    private Long member_id;
    private Long comment_id;
    private String content;
}
