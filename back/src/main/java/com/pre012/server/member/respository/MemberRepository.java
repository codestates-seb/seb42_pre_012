package com.pre012.server.member.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.MemberStatus;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndMemberStatus(String email, MemberStatus memberStatus);
    Member findById(long memberId);
}
