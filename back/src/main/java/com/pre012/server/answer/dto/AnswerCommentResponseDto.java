package com.pre012.server.answer.dto;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AnswerCommentResponseDto {

    //private Long memberId;
    //private Long answerId;
    private Long commentId;
    private String content;
    private String createdAt;
    //private LocalDateTime modifiedAt;
    //private String email;
    private String displayName;
    //private String profileImagePath;
}
