package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPatchDto {
    private Long memberId;
    private Long answerId;
    private String content;
}
