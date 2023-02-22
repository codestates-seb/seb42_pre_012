package com.pre012.server.member.repository;

import com.pre012.server.member.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pre012.server.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByEmailAndMemberStatus(String email, MemberStatus memberStatus);
}
