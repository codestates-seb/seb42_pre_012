package com.pre012.server.member.mapper;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.dto.MemberInfoDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import static com.pre012.server.member.dto.MemberDto.MemberInfo;
import static com.pre012.server.member.dto.MemberDto.MemberActivity;
import static com.pre012.server.member.dto.MemberInfoDto.WriterResponse;
import static com.pre012.server.member.dto.MemberInfoDto.MyQuestionResponse;
import static com.pre012.server.member.dto.MemberInfoDto.QuestionResponse;
import static com.pre012.server.member.dto.MemberInfoDto.AnswerResponse;
import static com.pre012.server.member.dto.MemberInfoDto.MemberAnswersResponseDto;
import static com.pre012.server.member.dto.MemberInfoDto.MemberQuestionsResponseDto;
import static com.pre012.server.member.dto.MemberInfoDto.MemberBookmarksResponseDto;
import static com.pre012.server.member.dto.MemberInfoDto.TagResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    /*
        -- 회원가입 Input
     */
    Member signUpDtoToMember(SignUpDto signUpDto);

    /*
        -- 회원 정보(프로필) 조회
     */
    // 1) Member 매핑
    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    MemberInfo memberToProfileMemberDto(Member member);

    // 2) 최종 Response 매핑
    default ProfileResponseDto memberToProfileResponseDto(Member member) {
        ProfileResponseDto response = new ProfileResponseDto();
        MemberInfo info = memberToProfileMemberDto(member);
        MemberActivity activity
                = new MemberActivity(member.getQuestionCnt(), member.getAnswerCnt());
        response.setMember(info);
        response.setActivity(activity);
        return response;
    }

    /*
        -- 회원 정보(내 질문) 조회
     */

    // 1) question 매핑
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "id", target = "questionId")
    @Mapping(target = "answerCnt", expression = "java(question.getAnswers() != null ? question.getAnswers().size() : 0)")
    QuestionResponse questionToQuestionResponse(Question question);
    List<QuestionResponse> questionsToQuestionDto(List<Question> questions);

    // 2) 최종 Response 매핑
    default MemberQuestionsResponseDto memberToMemberAnswersDto(List<Question> questions) {
        MemberQuestionsResponseDto response = new MemberQuestionsResponseDto();
        response.setQuestions(questionsToQuestionDto(questions));
        return response;
    }

    /*
        -- 회원 정보(북마크) 조회
     */

    // 1) 최종 Response 매핑
    default MemberBookmarksResponseDto memberToMemberBookmarksDto(List<Question> questions) {
        MemberBookmarksResponseDto response = new MemberBookmarksResponseDto();
        response.setBookmarks(questionsToQuestionDto(questions));
        return response;
    }


    /*
        -- 회원 정보(내 답변) 조회
     */
    @Mapping(source = "id", target = "memberId")
    WriterResponse memberToQuestionWriterDto(Member member);

    // 1) question 매핑
    @Mapping(source = "id", target = "questionId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    MyQuestionResponse questionToQuestionDto(Question question);
    List<MyQuestionResponse> questionsToMyQuestionDto(List<Question> questions);

    // 2) answer 매핑
    @Mapping(source = "id", target = "answerId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    AnswerResponse answerToAnswerDto(Answer answer);

    // 3) tag 매핑
    @Mapping(source = "tag.id", target = "tagId")
    @Mapping(source = "tag.name", target = "name")
    TagResponse questionTagToTagResponse(QuestionTag tag);

    // 4) 최종 Response 매핑
    default MemberAnswersResponseDto memberToMemberAnswersDto(Member member) {
        MemberAnswersResponseDto response = new MemberAnswersResponseDto();
        WriterResponse writer = memberToQuestionWriterDto(member);
        List<MyQuestionResponse> myQuestions = questionsToMyQuestionDto(member.getQuestions());
        response.setMember(writer);
        response.setQuestions(myQuestions);
        return response;
    }
}
