package com.pre012.server.question.mapper;

import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.entity.QuestionComment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionCommentMapper {

    QuestionComment questionCommentPostToQuestionComment(QuestionCommentDto.Post requestBody);
    QuestionComment questionCommentPatchToQuestionComment(QuestionCommentDto.Patch requestBody);

    List<QuestionCommentDto.Response> commentToQuestionCommentResponses(List<QuestionComment> questionComments);

}
