package com.pre012.server.member.mapper;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionTag;
import com.pre012.server.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import static com.pre012.server.member.dto.MemberDto.ProfileMember;
import static com.pre012.server.member.dto.MemberDto.ProfileActivity;
import static com.pre012.server.member.dto.MemberInfoDto.WriterResponse;
import static com.pre012.server.member.dto.MemberInfoDto.QuestionResponse;
import static com.pre012.server.member.dto.MemberInfoDto.AnswerResponse;
import static com.pre012.server.member.dto.MemberInfoDto.MemberAnswersDto;
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
    ProfileMember memberToProfileMemberDto(Member member);

    // 2) 최종 Response 매핑
    default ProfileResponseDto memberToProfileResponseDto(Member member) {
        ProfileResponseDto response = new ProfileResponseDto();
        ProfileMember profileMember = memberToProfileMemberDto(member);
        ProfileActivity profileActivity
                = new ProfileActivity(member.getQuestionCnt(), member.getAnswerCnt());
        response.setMember(profileMember);
        response.setActivity(profileActivity);
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
    QuestionResponse questionToQuestionDto(Question question);
    List<QuestionResponse> questionsToQuestionDto(List<Question> questions);

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
    default MemberAnswersDto memberToMemberAnswersDto(Member member) {
        MemberAnswersDto response = new MemberAnswersDto();
        WriterResponse writer = memberToQuestionWriterDto(member);
        List<QuestionResponse> responseQuestions = questionsToQuestionDto(member.getQuestions());
        response.setMember(writer);
        response.setQuestions(responseQuestions);
        return response;
    }
}
