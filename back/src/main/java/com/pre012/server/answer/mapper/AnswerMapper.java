package com.pre012.server.answer.mapper;

import com.pre012.server.answer.controller.AnswerController;
import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        answer.setId(answerPatchDto.getAnswer_id());
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
        String image_path = answer.getImage_path();
        LocalDateTime createdAt = answer.getCreatedAt();
        LocalDateTime modifiedAt = answer.getModifiedAt();

        AnswerResponseDto answerResponseDto = new AnswerResponseDto( member_id, question_id, answer_id,
                content, likeCnt,image_path,createdAt,modifiedAt);

        return answerResponseDto;
    }
    default List<AnswerResponseDto> answersToAnswerResponseDtos(List<Answer> answers){
        if ( answers == null ) {
            return null;
        }

        List<AnswerResponseDto> list = new ArrayList<>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( answerToAnswerResponseDto( answer ) );
        }

        return list;
    }

    default AnswerMultiResponseDto answerToMultiResponseDto(Answer answer, List<AnswerCommentResponseDto> comments){
        AnswerMultiResponseDto answerMultiResponseDto = new AnswerMultiResponseDto();
        answerMultiResponseDto.setAnswer(answerToAnswerResponseDto(answer));
        answerMultiResponseDto.setComments(comments);
        answerMultiResponseDto.setMember(answerToMemberResponse(answer));
        return answerMultiResponseDto;
    }

    default List<AnswerMultiResponseDto> answersToAnswerMultiResponseDtos(List<Answer> answers){
        if ( answers == null ) {
            return null;
        }

        List<AnswerMultiResponseDto> list = new ArrayList<>( answers.size() );
        for ( Answer answer : answers ) {
            List<AnswerComment> comments = answer.getComments();
            list.add( answerToMultiResponseDto( answer, answerCommentsToAnswerCommentResponseDtos(comments)));
        }

        return list;
    }

    default MemberInfoDto.WriterResponse answerToMemberResponse(Answer answer) {
        Member member = answer.getMember();
        return new MemberInfoDto.WriterResponse(
                member.getId(),
                member.getEmail(),
                member.getDisplayName(),
                member.getProfileImagePath()
        );
    }

    /**
    얘는 왜 이걸 또 추가한거야? -> 제가 너무나도 무능력하여.. 머리를 싸매보고 정은님 pr올리신 것도 참고해서 response를 만들려다보니
    answerCommentmapper의 기능을 여기서 이용해야 했습니다..
    그래서 여기 한번 더 정의해둔겁니다. 너무 지저분하게 코드가 짜여져서 정말 죄송합니다.
     */
    default AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if ( answerComment == null ) {
            return null;
        }
        Long member_id = answerComment.getMember().getId();
        Long answer_id = answerComment.getAnswer().getId();
        Long comment_id = answerComment.getId();
        String content = answerComment.getContent();
        LocalDateTime createdAt = answerComment.getCreatedAt();
        LocalDateTime modifiedAt = answerComment.getModifiedAt();
        String displayName = answerComment.getMember().getDisplayName();
        String email = answerComment.getMember().getEmail();
        String profileImagePath = answerComment.getMember().getProfileImagePath();

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto(member_id,answer_id,comment_id,content,createdAt,modifiedAt,
                displayName,email,profileImagePath);
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
