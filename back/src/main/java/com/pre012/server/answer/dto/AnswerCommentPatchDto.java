package com.pre012.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AnswerCommentPatchDto {
    @NotNull
    private Long memberId;
    private Long commentId;
    @NotBlank
    private String content;
}
