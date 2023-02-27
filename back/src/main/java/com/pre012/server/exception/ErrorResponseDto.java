package com.pre012.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;

    public static ErrorResponseDto of(int status, String exceptionMessage) {
        return new ErrorResponseDto(status, exceptionMessage);
    }

    public static ErrorResponseDto of(ExceptionCode exceptionCode) {
        return new ErrorResponseDto(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

}