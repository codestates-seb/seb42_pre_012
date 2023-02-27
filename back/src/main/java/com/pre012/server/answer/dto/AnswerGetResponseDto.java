package com.pre012.server.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AnswerGetResponseDto {
    List<AnswerMultiResponseDto> answers;
}
