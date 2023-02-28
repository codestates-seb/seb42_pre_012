package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AnswerCommentPostDto {
    @NotNull
    private Long memberId;

    @NotBlank
    private String content;
}
