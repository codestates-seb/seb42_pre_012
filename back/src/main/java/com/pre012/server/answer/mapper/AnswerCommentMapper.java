package com.pre012.server.answer.mapper;

import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerCommentMapper {
    default AnswerComment answerCommentPostDtoToAnswerComment(AnswerCommentPostDto answerCommentPostDto){
        if(answerCommentPostDto==null){
            return null;
        }

        Member member = new Member();
        member.setId(answerCommentPostDto.getMember_id());

        AnswerComment answerComment = new AnswerComment();
        answerComment.setMember(member);
        answerComment.setContent(answerCommentPostDto.getContent());

        return answerComment;
    }
    default AnswerComment answerCommentPatchDtoToAnswerComment(AnswerCommentPatchDto answerCommentPatchDto){
        if(answerCommentPatchDto==null){
            return null;
        }
        Member member = new Member();
        member.setId(answerCommentPatchDto.getMember_id());

        AnswerComment answerComment = new AnswerComment();
        answerComment.setMember(member);
        answerComment.setContent(answerCommentPatchDto.getContent());

        return answerComment;
    }
    default AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if ( answerComment == null ) {
            return null;
        }
        Long member_id = answerComment.getMember().getId();
        Long answer_id = answerComment.getId();
        String content = answerComment.getContent();
        LocalDateTime createdAt = answerComment.getCreatedAt();
        LocalDateTime modifiedAt = answerComment.getModifiedAt();
        Member member = answerComment.getMember();
        Answer answer = answerComment.getAnswer();

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto(content,answer,member,createdAt,modifiedAt);
        return answerCommentResponseDto;
    }
}
