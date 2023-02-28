package com.pre012.server.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.MemberStatus;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByEmailAndMemberStatus(String email, MemberStatus memberStatus);
    Optional<Member> findFirstByEmailOrDisplayName(String email, String displayName);

}
