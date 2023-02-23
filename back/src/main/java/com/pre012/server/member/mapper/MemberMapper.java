package com.pre012.server.member.mapper;

import com.pre012.server.member.dto.MemberDto;
import com.pre012.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Profile;

import static com.pre012.server.member.dto.MemberDto.SignUpDto;
import static com.pre012.server.member.dto.MemberDto.ProfileResponseDto;
import static com.pre012.server.member.dto.MemberDto.ProfileMember;
import static com.pre012.server.member.dto.MemberDto.ProfileActivity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member signUpDtoToMember(SignUpDto signUpDto);
    @Mapping(source = "id", target = "memberId")
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
}
