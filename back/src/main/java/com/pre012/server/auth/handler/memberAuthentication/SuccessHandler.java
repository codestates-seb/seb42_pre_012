package com.pre012.server.auth.handler.memberAuthentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;
import com.pre012.server.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

import static com.pre012.server.auth.dto.AuthDto.LoginResponse;

/*
 * 인증 성공 핸들러
 */
@Slf4j
public class SuccessHandler implements AuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("# Authenticated successfully!");
        sendSuccessResponse(response, authentication);
    }

    private void sendSuccessResponse(HttpServletResponse response, Authentication authentication) throws IOException {
        Member member = (Member) authentication.getPrincipal();

        String accessToken = delegateAccessToken(member); 
        String refreshToken = delegateRefreshToken(member);

        Gson gson = new Gson();
        LoginResponse memberInfo = new LoginResponse(member.getId(), member.getProfileImagePath());

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(gson.toJson(memberInfo, LoginResponse.class));
    }

    
    // Access Token 발급 
    private String delegateAccessToken(Member member) { 
        String accessToken = "구현하기";
        return accessToken;    
    }

    // Access Token 발급 
    private String delegateRefreshToken(Member member) { 
        String refreshToken = "구현하기";
        return refreshToken;    
    }
}
