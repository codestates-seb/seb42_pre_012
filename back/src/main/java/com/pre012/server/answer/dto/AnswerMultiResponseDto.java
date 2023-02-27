package com.pre012.server.answer.dto;

import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.enums.LikeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AnswerMultiResponseDto {
    private Long answerId;
    private String content;
    private int likeCnt;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LikeType likeStatus;
    private MemberInfoDto.WriterResponse writer;
    private List<AnswerCommentResponseDto> comments;
}

