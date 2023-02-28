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
    @NotBlank(message = "댓글 내용은 공백이 아니어야 합니다.")
    private String content;
}
