package com.pre012.server.auth.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/* 
데이터베이스에서 조회한 회원의 이메일 정보를 이용해 권한 정보 리스트 생성
*/
@Component
public class CustomAuthorityUtils { 
    
    @Value("${mail.address.admin}")
    private String adminMailAddress;

    // 관리자용 권한 목록
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    // 회원용 권한 목록
    private final List<String> USER_ROLES_STRING = List.of("USER");

    // 권한 목록을 List<GrantedAuthority> 객체로 생성
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
       List<GrantedAuthority> authorities = roles.stream()
                                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                                .collect(Collectors.toList());
       return authorities;
    }

    // 이메일 주소와 동일하다면 관리자용 권한/아니면 회원용 권한 부여
    public List<String> createRoles(String email) {
        return (email.equals(adminMailAddress)) ? ADMIN_ROLES_STRING : USER_ROLES_STRING;
    }
}