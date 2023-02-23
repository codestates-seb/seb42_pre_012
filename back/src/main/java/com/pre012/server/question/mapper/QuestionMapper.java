package com.pre012.server.question.mapper;

import com.pre012.server.member.dto.MemberDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostToQuestion(QuestionDto.Post requestBody);

    Question questionPatchToQuestion(QuestionDto.Patch requestBody);

    QuestionDto.Response questionToQuestionResponse(Question question);

//    List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions);

    /**
     * 질문 목록 조회 및 필터링 & 질문 검색 결과 Response 로 변환해주는 mapper
     * questionResponse
     * memberResponse (질문 작성자)
     * answerCnt
     * tagResponse (2순위) <- 추가 필요 @@
     */
    default List<QuestionDto.searchResponse> questionsToSearchResponses(List<Question> questions) {
        return questions.stream()
                .map(q ->
                {
                    QuestionDto.searchResponse s = new QuestionDto.searchResponse(
                            questionToQuestionResponse(q),
                            questionToMemberResponse(q),
                            q.getAnswerCnt()
                    );
                    return s;
                })
                .collect(Collectors.toList());

    }

    /**
     * 질문 상세 조회 Response 로 변환해주는 mapper
     * questionResponse
     * commentResponse
     * memberResponse (질문 작성자)
     * isBookmarked
     * likeStatus
     * tagResponse (2순위)
     */
    default QuestionDto.getResponse questionToGetResponse(Question question, List<QuestionCommentDto.Response> commentResponses, boolean isBookmarked, String likeStatus) {
        return new QuestionDto.getResponse(
                questionToQuestionResponse(question),
                commentResponses,
                questionToMemberResponse(question),
                isBookmarked,
                likeStatus
        );
    }


    /**
     * Question 을 쓴 member 의 Response DTO를 만들어주는 mapper
     */
    default MemberDto.SimpleInfo questionToMemberResponse(Question question) {
        Member member = question.getMember();
        return new MemberDto.SimpleInfo(
                member.getId(),
                member.getEmail(),
                member.getDisplayName(),
                member.getProfileImagePath()
        );
    }

}
