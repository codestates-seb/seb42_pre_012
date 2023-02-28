package com.pre012.server.question.mapper;

import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.entity.QuestionComment;
import org.mapstruct.Mapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionCommentMapper {

    QuestionComment questionCommentRequestToQuestionComment(QuestionCommentDto.Request requestBody);


    /**
     * 질문 상세 조회에 보낼 ResponseDTO List 만드는 데에 쓸 mapper
     * comment Writer 추가 - member.displayName
     * createdAt 형식 추가해서 String 으로 return
     */
    default QuestionCommentDto.Response commentToQuestionCommentResponse(QuestionComment questionComment) {
        return new QuestionCommentDto.Response(
                questionComment.getId(),
                questionComment.getContent(),
                questionComment.getMember().getDisplayName(),
                questionComment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    List<QuestionCommentDto.Response> commentToQuestionCommentResponses(List<QuestionComment> questionComments);

}
