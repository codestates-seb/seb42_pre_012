package com.pre012.server.answer.dto;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerCommentResponseDto {

    private String content;

    private Answer answer;

    private Member member;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public AnswerCommentResponseDto(String content, Answer answer, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.content = content;
        this.answer = answer;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
