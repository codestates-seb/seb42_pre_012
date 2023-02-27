package com.pre012.server.answer.mapper;

import com.pre012.server.answer.controller.AnswerController;
import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.repository.AnswerLikeRepository;
import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    default Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto){
        if(answerPostDto==null){
            return null;
        }

        Member member = new Member();
        member.setId(answerPostDto.getMemberId());

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
        member.setId(answerPatchDto.getMemberId());

        Answer answer = new Answer();
        answer.setId(answerPatchDto.getAnswerId());
        answer.setMember(member);
        answer.setContent(answerPatchDto.getContent());

        return answer;
    }
    default AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }
//        Long member_id = answer.getMember().getId();
//        Long question_id = answer.getQuestion().getId();
        Long answer_id = answer.getId();
        String content = answer.getContent();
        int likeCnt = answer.getLikeCnt();
        String imagePath = answer.getImagePath();
        String createdAt = answer.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String modifiedAt = answer.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer_id,
                content, likeCnt,imagePath,createdAt,modifiedAt);

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

    default AnswerMultiResponseDto answerToMultiResponseDto(Answer answer, List<AnswerCommentResponseDto> comments, LikeType likeType ){
        AnswerMultiResponseDto answerMultiResponseDto = new AnswerMultiResponseDto();
        answerMultiResponseDto.setAnswerId(answer.getId());
        answerMultiResponseDto.setContent(answer.getContent());
        answerMultiResponseDto.setLikeCnt(answer.getLikeCnt());
        answerMultiResponseDto.setImagePath(answer.getImagePath());
        answerMultiResponseDto.setCreatedAt(answer.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        answerMultiResponseDto.setModifiedAt(answer.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        answerMultiResponseDto.setLikeStatus(likeType);
        answerMultiResponseDto.setComments(comments);
        answerMultiResponseDto.setWriter(answerToMemberResponse(answer));
        return answerMultiResponseDto;
    }

    default List<AnswerMultiResponseDto> answersToAnswerMultiResponseDtos(List<Answer> answers,Long memberId){
        if ( answers == null ) {
            return null;
        }

        List<AnswerMultiResponseDto> list = new ArrayList<>( answers.size() );
        for ( Answer answer : answers ) {
            List<AnswerComment> comments = answer.getComments();
            List<AnswerLike> answerLikes = answer.getAnswerLikes();
            AnswerLike answerLike = answerLikes.stream().filter(answerLike1 -> answerLike1.getMember().getId()==memberId).findFirst().orElseGet(AnswerLike::new);
            list.add( answerToMultiResponseDto( answer, answerCommentsToAnswerCommentResponseDtos(comments),answerLike.getLikeType()));
        }

        return list;
    }

    default AnswerGetResponseDto answerMultiToAnswerGetResponseDto(List<AnswerMultiResponseDto> answers){
        return new AnswerGetResponseDto(answers);
    }

    default MemberInfoDto.WriterResponse answerToMemberResponse(Answer answer) {
        Member member = answer.getMember();
        return new MemberInfoDto.WriterResponse(
                member.getId(),
                member.getEmail(),
                member.getDisplayName(),
                member.getProfileImage()
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
