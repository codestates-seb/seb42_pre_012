package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPatchDto {
    private Long member_id;
    private Long answer_id;
    private String content;
}
