package com.pre012.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

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
    public static class LogoutDto {
        @Positive(message = "잘못된 memberId 입니다.")
        private Long memberId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReissueDto {
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private Long memberId;
        private String displayName;
        private String profileColor;
    }

}
