package com.pre012.server.auth.util;

import com.google.gson.Gson;
import com.pre012.server.exception.ErrorResponseDto;
import com.pre012.server.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response,
                                         HttpStatus status,
                                         String exceptionMessage) throws IOException {
        Gson gson = new Gson();
        ErrorResponseDto errorResponse = ErrorResponseDto.of(401, exceptionMessage);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponseDto.class));
    }

    public static void sendErrorResponse(HttpServletResponse response,
                                         HttpStatus status,
                                         ExceptionCode exceptionCode) throws IOException {
        Gson gson = new Gson();
        ErrorResponseDto errorResponse = ErrorResponseDto.of(exceptionCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponseDto.class));
    }
}