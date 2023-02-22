package com.pre012.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private Long memberId;
        private String profileImagePath;
    }
}
