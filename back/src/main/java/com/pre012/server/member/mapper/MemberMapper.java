package com.pre012.server.member.mapper;

import com.pre012.server.member.dto.MemberDto;
import com.pre012.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static com.pre012.server.member.dto.MemberDto.SignUpDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member signUpDtoToMember(SignUpDto signUpDto);
}
