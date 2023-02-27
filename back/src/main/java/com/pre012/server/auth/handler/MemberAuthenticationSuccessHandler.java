package com.pre012.server.auth.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pre012.server.common.dto.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;
import com.pre012.server.auth.util.JWTTokenizer;
import com.pre012.server.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

import static com.pre012.server.auth.dto.AuthDto.LoginResponse;

/*
 * 인증 성공 핸들러
 */
@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private final JWTTokenizer tokenizer;

    public MemberAuthenticationSuccessHandler(JWTTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

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
        String imgPath = member.getProfileImagePath() != null ? member.getProfileImagePath() : "";
        LoginResponse memberInfo = new LoginResponse(member.getId(), imgPath);
        SingleResponseDto<LoginResponse> responseDto = new SingleResponseDto<>(memberInfo);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("RefreshToken", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(gson.toJson(responseDto, SingleResponseDto.class));
    }

    
    // Access Token 발급
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getId());
        claims.put("roles", member.getRoles());
        String subject = member.getEmail();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());

        return tokenizer.generateAccessToken(claims, subject, expirationDate, base64EncodedSecretKey);
    }

    // Refresh Token 발급
    private String delegateRefreshToken(Member member) { 
        String subject = member.getEmail();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());

        return tokenizer.generateRefreshToken(subject, expirationDate, base64EncodedSecretKey);
    }
}
