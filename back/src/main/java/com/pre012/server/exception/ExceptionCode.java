package com.pre012.server.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_LOGIN_FAILED(4000, "로그인 인증에 실패했습니다."),
    MEMBER_UNAUTHORIZED(4001, "접근 권한이 없습니다."),
    TOKEN_EXPIRED(4002, "만료된 ACCESS TOKEN 입니다."),
    TOKEN_DAMAGED(4002, "손상된 ACCESS TOKEN 입니다."),

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
