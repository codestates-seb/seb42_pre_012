package com.pre012.server.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pre012.server.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
