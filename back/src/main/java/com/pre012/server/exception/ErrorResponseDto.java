package com.pre012.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;

    public static ErrorResponseDto of(int status, String exceptionMessage) {
        return new ErrorResponseDto(status, exceptionMessage);
    }

    public static ErrorResponseDto of(BindingResult bindingResult) {
        int status = ExceptionCode.PARAMETER_NOT_VALID.getStatus();
        String message = bindingResult.getFieldError().getDefaultMessage();
        return new ErrorResponseDto(status, message);
    }

    public static ErrorResponseDto of(ExceptionCode exceptionCode) {
        return new ErrorResponseDto(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

    public static ErrorResponseDto of(String message) {
        int status = ExceptionCode.PARAMETER_NOT_VALID.getStatus();
        return new ErrorResponseDto(status, message);
    }

}