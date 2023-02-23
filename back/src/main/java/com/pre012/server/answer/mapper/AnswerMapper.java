package com.pre012.server.answer.mapper;

import com.pre012.server.answer.controller.AnswerController;
import com.pre012.server.answer.dto.AnswerPostDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

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
}
