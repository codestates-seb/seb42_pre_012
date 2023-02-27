package com.pre012.server.question.mapper;

import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import com.pre012.server.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionRequestToQuestion(QuestionDto.Request requestBody);


    @Mapping(source = "id", target = "questionId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    QuestionDto.Response questionToQuestionResponse(Question question);


    /**
     * 질문 목록 조회 및 필터링 & 질문 검색 결과 Response 로 변환해주는 mapper
     */
    default List<QuestionDto.searchResponse> questionsToSearchResponses(List<Question> questions) {
        return questions.stream()
                .map(q -> {
                    QuestionDto.searchResponse sr = new QuestionDto.searchResponse(
                            questionToQuestionResponse(q),
                            questionToMemberResponse(q),
                            questionToTagResponses(q),
                            q.getAnswerCnt()
                    );
                    return sr;
                })
                .collect(Collectors.toList());
    }

    /**
     * 질문 상세 조회 Response 로 변환해주는 mapper
     */
    default QuestionDto.getResponse questionToGetResponse(Question question, List<QuestionCommentDto.Response> commentResponses, boolean isBookmarked, LikeType likeStatus) {
        return new QuestionDto.getResponse(
                questionToQuestionResponse(question),
                questionToTagResponses(question),
                commentResponses,
                questionToMemberResponse(question),
                isBookmarked,
                likeStatus
        );
    }


    /**
     * Question 을 쓴 member 의 Response DTO를 만들어주는 mapper
     */
    default MemberInfoDto.WriterResponse questionToMemberResponse(Question question) {
        Member member = question.getMember();
        return new MemberInfoDto.WriterResponse(
                member.getId(),
                member.getEmail(),
                member.getDisplayName(),
                member.getProfileImagePath()
        );
    }

    /**
     * Question 에 있는 tag 의 Resposne DTO 를 만들어주는 mapper
     */
    default List<TagDto.Response> questionToTagResponses(Question question) {
        return question.getQuestionTags().stream()
                .map(questionTag -> {
                    TagDto.Response response = new TagDto.Response(
                            questionTag.getTag().getId(),
                            questionTag.getTag().getName()
                    );
                    return response;
                })
                .collect(Collectors.toList());
    }

}
