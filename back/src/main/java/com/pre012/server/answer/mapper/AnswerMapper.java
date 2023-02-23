package com.pre012.server.answer.mapper;

import com.pre012.server.answer.controller.AnswerController;
import com.pre012.server.answer.dto.AnswerPatchDto;
import com.pre012.server.answer.dto.AnswerPostDto;
import com.pre012.server.answer.dto.AnswerResponseDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    default Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto){
        if(answerPostDto==null){
            return null;
        }

        Member member = new Member();
        member.setId(answerPostDto.getMember_id());

        Answer answer = new Answer();
        answer.setMember(member);
        answer.setContent(answerPostDto.getContent());

        return answer;
    }
    default Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto){
        if(answerPatchDto==null){
            return null;
        }
        Member member = new Member();
        member.setId(answerPatchDto.getMember_id());

        Answer answer = new Answer();
        answer.setMember(member);
        answer.setContent(answerPatchDto.getContent());

        return answer;
    }
    default AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }
        Long member_id = answer.getMember().getId();
        Long question_id = answer.getQuestion().getId();
        Long answer_id = answer.getId();
        String content = answer.getContent();
        int likeCnt = answer.getLikeCnt();
        LocalDateTime createdAt = answer.getCreatedAt();
        LocalDateTime modifiedAt = answer.getModifiedAt();
        List<AnswerComment> comments = answer.getComments();

        AnswerResponseDto answerResponseDto = new AnswerResponseDto( member_id, question_id, answer_id,
                content, likeCnt, createdAt,modifiedAt,comments);

        return answerResponseDto;
    }
}
