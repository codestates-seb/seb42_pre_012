package com.pre012.server.member.mapper;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import static com.pre012.server.member.dto.MemberDto.ProfileMember;
import static com.pre012.server.member.dto.MemberDto.ProfileActivity;
import static com.pre012.server.member.dto.MemberAnswersDto.AnswersResponseDto;
import static com.pre012.server.member.dto.MemberAnswersDto.QuestionMember;
import static com.pre012.server.member.dto.MemberAnswersDto.QuestionDto;
import static com.pre012.server.member.dto.MemberAnswersDto.AnswerDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    // -- 회원가입
    Member signUpDtoToMember(SignUpDto signUpDto);

    // -- 회원 정보(프로필) 조회
    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    ProfileMember memberToProfileMemberDto(Member member);
    default ProfileResponseDto memberToProfileResponseDto(Member member) {
        ProfileResponseDto response = new ProfileResponseDto();
        ProfileMember profileMember = memberToProfileMemberDto(member);
        ProfileActivity profileActivity
                = new ProfileActivity(member.getQuestionCnt(), member.getAnswerCnt());
        response.setMember(profileMember);
        response.setActivity(profileActivity);
        return response;
    }

    // -- 회원 정보(내 답변) 조회
    @Mapping(source = "id", target = "memberId")
    QuestionMember memberToQuestionMemberDto(Member member);

    @Mapping(source = "id", target = "questionId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    QuestionDto questionToQuestionDto(Question question);
    @Mapping(source = "id", target = "answerId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "modifiedAt", target = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm")
    AnswerDto answerToAnswerDto(Answer question);
    List<QuestionDto> questionsToQuestionDto(List<Question> questions);

    default AnswersResponseDto memberToAnswersResponseDto(Member member) {
        AnswersResponseDto response = new AnswersResponseDto();
        QuestionMember questionMember = memberToQuestionMemberDto(member);
        List<QuestionDto> responseQuestions = questionsToQuestionDto(member.getQuestions());
        response.setMember(questionMember);
        response.setQuestions(responseQuestions);
        return response;
    }
}
