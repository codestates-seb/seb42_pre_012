package com.pre012.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
    
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpDto {
        private String email;
        private String password;
        private String displayName;
    }

}
