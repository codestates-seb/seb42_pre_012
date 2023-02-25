package com.pre012.server.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AnswerMultiResponseDto {
    private AnswerResponseDto answer;
    private MemberInfoDto.WriterResponse member;
    private List<AnswerCommentResponseDto> comments;
}

