package com.pre012.server.advice;

import com.google.gson.Gson;
import com.pre012.server.exception.ErrorResponseDto;
import com.pre012.server.exception.ExceptionCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Aspect
@Component
public class FilterChainProxyAdvice {

    @Around("execution(public void org.springframework.security.web.FilterChainProxy.doFilter(..))")
    public void handleRequestRejectedException (ProceedingJoinPoint pjp) throws Throwable {
        try {
            pjp.proceed();
        } catch (RequestRejectedException e) {
            HttpServletResponse response = (HttpServletResponse) pjp.getArgs()[1];
            sendErrorResponse(response, ExceptionCode.URL_NOT_VALID);
        } catch (IllegalStateException e) {
            HttpServletResponse response = (HttpServletResponse) pjp.getArgs()[1];
            sendErrorResponse(response, ExceptionCode.SYSTEM_ERROR);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        Gson gson = new Gson();
        ErrorResponseDto errorResponse = ErrorResponseDto.of(exceptionCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponseDto.class));
    }
}