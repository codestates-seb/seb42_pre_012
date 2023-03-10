package com.pre012.server.auth.handler;

import com.pre012.server.auth.util.ErrorResponder;
import com.pre012.server.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    인증 과정에서 AuthenticationException 이 발생할 경우 호출
    (SignatureException, ExpiredJwtException 등)
 */
@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Exception exception = (Exception) request.getAttribute("exception");

        if (exception == null) {
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ExceptionCode.MEMBER_UNAUTHORIZED);
        }
        logExceptionMessage(authException, exception);
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message);
    }
}