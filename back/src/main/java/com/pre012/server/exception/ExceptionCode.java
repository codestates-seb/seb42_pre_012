package com.pre012.server.exception;

import lombok.Getter;

public enum ExceptionCode {

    PARAMETER_NOT_VALID(3000, "유효하지 않은 요청값입니다"),

    MEMBER_LOGIN_FAILED(4000, "로그인 인증에 실패했습니다."),
    MEMBER_UNAUTHORIZED(4001, "접근 권한이 없습니다."),
    TOKEN_EXPIRED(4002, "만료된 TOKEN 입니다."),
    TOKEN_DAMAGED(4003, "손상된 TOKEN 입니다."),
    TOKEN_NOT_VALID(4004, "유효하지 않은 TOKEN 입니다."),
    EMAIL_ALREADY_EXISTS(4005, "이미 존재하는 이메일 입니다."),
    NICKNAME_ALREADY_EXISTS(4006, "이미 존재하는 닉네임 입니다."),
    MEMBER_NOT_FOUND(4007, "유효하지 않은 회원 입니다."),

    SYSTEM_ERROR(5000, "시스템 오류가 발생했습니다."),
    ;

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
