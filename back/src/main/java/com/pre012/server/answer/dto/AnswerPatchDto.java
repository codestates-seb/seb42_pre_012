package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AnswerPatchDto {
    @NotNull
    @Positive
    private Long memberId;
    private Long answerId;
    @NotBlank(message = "답변 내용은 공백이 아니어야 합니다.")
    private String content;
}
