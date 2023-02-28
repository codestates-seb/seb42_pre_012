package com.pre012.server.member.mapper;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.dto.MemberDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import com.pre012.server.member.dto.MemberDto.SignUpDto;
import com.pre012.server.member.dto.MemberDto.ModifyDto;
import com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import com.pre012.server.member.dto.MemberDto.MemberInfo;
import com.pre012.server.member.dto.MemberDto.MemberSimpleInfo;
import com.pre012.server.member.dto.MemberDto.MemberActivity;
import com.pre012.server.member.dto.MemberInfoDto.WriterResponse;
import com.pre012.server.member.dto.MemberInfoDto.QuestionResponse;
import com.pre012.server.member.dto.MemberInfoDto.MemberAnswersResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberQuestionsResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.MemberBookmarksResponseDto;
import com.pre012.server.member.dto.MemberInfoDto.TagResponse;
import com.pre012.server.member.dto.MemberInfoDto.AnswerResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    /*
        -- 회원가입 Input
     */
    Member signUpDtoToMember(SignUpDto signUpDto);

    /*
    -- 회원 수정 Input
 */
    Member modifyDtoToMember(ModifyDto modifyDto);

    // ------------------------------------------------------------------------

    /*
        -- 간단 회원 정보(가입일시, 수정일시 제외)
     */
    @Mapping(source = "id", target = "memberId")
    MemberSimpleInfo memberToMemberSimpleInfoDto(Member member);

    /*
        -- 회원 정보(프로필) 조회
     */
    // 1) Member 매핑
    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    MemberInfo memberToProfileMemberResponse(Member member);

    // 2) 최종 Response 매핑
    default ProfileResponseDto memberToProfileResponseDto(Member member) {
        ProfileResponseDto response = new ProfileResponseDto();
        MemberInfo info = memberToProfileMemberResponse(member);
        MemberActivity activity
                = new MemberActivity(member.getQuestionCnt(), member.getAnswerCnt());
        response.setMember(info);
        response.setActivity(activity);
        return response;
    }

    /*
        -- 회원 정보(내 질문) 조회
     */

    // 1) writer(글 작성자) 매핑
    @Mapping(source = "id", target = "memberId")
    WriterResponse memberToQuestionWriterResponse(Member member);

    // 2) question 매핑 - 답변, 북마크 매핑에도 사용
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "id", target = "questionId")
    @Mapping(source = "member", target = "writer")
    @Mapping(source = "questionTags", target = "tags")
    @Mapping(target = "answerCnt", expression = "java(question.getAnswers() != null ? question.getAnswers().size() : 0)")
    QuestionResponse questionToQuestionResponse(Question question);
    List<QuestionResponse> questionsToQuestionsResponse(List<Question> questions);

    // 3) 최종 Response 매핑
    default MemberQuestionsResponseDto questionsToMemberQuestionsDto(List<Question> questions) {
        MemberQuestionsResponseDto response = new MemberQuestionsResponseDto();
        response.setQuestions(questionsToQuestionsResponse(questions));
        response.getQuestions().forEach(q -> q.setWriter(null));
        response.getQuestions().forEach(q -> q.setAnswers(null));
        return response;
    }

    /*
        -- 회원 정보(북마크) 조회
     */

    // 1) 최종 Response 매핑
    default MemberBookmarksResponseDto memberToMemberBookmarksDto(List<Question> questions) {
        MemberBookmarksResponseDto response = new MemberBookmarksResponseDto();
        response.setQuestions(questionsToQuestionsResponse(questions));
        response.getQuestions().forEach(q -> q.setAnswers(null));
        return response;
    }

    /*
        -- 회원 정보(내 답변) 조회
     */

    // 1) tag 매핑
    @Mapping(source = "tag.id", target = "tagId")
    @Mapping(source = "tag.name", target = "name")
    TagResponse questionTagToTagResponse(QuestionTag tag);

    // 2) answer 매핑
    @Mapping(source = "id", target = "answerId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    AnswerResponse answerToAnswerResponse(Answer answer);

    // 3) 최종 Response 매핑
    default MemberAnswersResponseDto questionsToMemberAnswersDto(List<Question> questions) {
        MemberAnswersResponseDto response = new MemberAnswersResponseDto();
        List<QuestionResponse> questionsIAnswered = questionsToQuestionsResponse(questions);
        response.setQuestions(questionsIAnswered);
        return response;
    }

}
