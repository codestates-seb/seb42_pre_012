package com.pre012.server.answer.mapper;

import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerCommentMapper {
    default AnswerComment answerCommentPostDtoToAnswerComment(AnswerCommentPostDto answerCommentPostDto){
        if(answerCommentPostDto==null){
            return null;
        }

        Member member = new Member();
        member.setId(answerCommentPostDto.getMemberId());

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
        member.setId(answerCommentPatchDto.getMemberId());

        AnswerComment answerComment = new AnswerComment();
        answerComment.setId(answerCommentPatchDto.getCommentId());
        answerComment.setMember(member);
        answerComment.setContent(answerCommentPatchDto.getContent());

        return answerComment;
    }

    default AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if ( answerComment == null ) {
            return null;
        }
//        Long member_id = answerComment.getMember().getId();
//        Long answer_id = answerComment.getAnswer().getId();
        Long commentId = answerComment.getId();
        String content = answerComment.getContent();
        String createdAt = answerComment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        LocalDateTime modifiedAt = answerComment.getModifiedAt();
        String displayName = answerComment.getMember().getDisplayName();
//        String email = answerComment.getMember().getEmail();
//        String profileImagePath = answerComment.getMember().getProfileImagePath();

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto(commentId,content,createdAt,displayName);
        return answerCommentResponseDto;
    }

    default List<AnswerCommentResponseDto> answerCommentsToAnswerCommentResponseDtos(List<AnswerComment> comments) {
        List<AnswerCommentResponseDto> list = new ArrayList<>( comments.size() );
        for ( AnswerComment comment : comments ) {
            list.add( answerCommentToAnswerCommentResponseDto(comment) );
        }

        return list;
    }
}
