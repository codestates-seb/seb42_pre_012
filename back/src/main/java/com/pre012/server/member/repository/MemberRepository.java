package com.pre012.server.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pre012.server.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByEmailOrDisplayName(String email, String displayName);

}