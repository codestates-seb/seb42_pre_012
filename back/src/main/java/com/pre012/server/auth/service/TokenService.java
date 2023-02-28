package com.pre012.server.auth.service;

import com.pre012.server.auth.entity.Token;
import com.pre012.server.auth.repository.TokenRepository;
import com.pre012.server.auth.util.JWTTokenizer;
import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ExceptionCode;
import com.pre012.server.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JWTTokenizer tokenizer;

    public TokenService(TokenRepository tokenRepository, JWTTokenizer tokenizer) {
        this.tokenRepository = tokenRepository;
        this.tokenizer = tokenizer;
    }

    @Transactional
    public Map<String, String> reissueAccessToken(String refreshToken) {
        Token token = findValidToken(refreshToken);
        Member member = token.getMember();

        Map<String, String> response = new HashMap<>();
        if (isVerifiedToken(token)) {
            response.put("accessToken", delegateAccessToken(member));
            response.put("refreshToken", delegateRefreshToken(member));
            return response;
        }
        throw new BusinessLogicException(ExceptionCode.TOKEN_EXPIRED);
    }

    @Transactional
    public void changeTokenValid(Long memberId) {
        Token token = tokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_NOT_VALID));
        token.setValid(false);
    }

    public Token findValidToken(String refreshToken) {
        return tokenRepository.findByRefreshTokenAndValid(refreshToken, true)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_NOT_VALID));
    }

    public boolean isVerifiedToken(Token token) {
        return token.getExpirationDate().isAfter(LocalDateTime.now());
    }

    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getId());
        claims.put("roles", member.getRoles());
        String subject = member.getEmail();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());

        return tokenizer.generateAccessToken(claims, subject, expirationDate, base64EncodedSecretKey);
    }

    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        String refreshToken = tokenizer.generateRefreshToken(subject, expirationDate, base64EncodedSecretKey);

        saveRefreshToken(member, refreshToken, expirationDate);
        return refreshToken;
    }

    public void saveRefreshToken(Member member, String refreshToken, Date expirationDate) {
        Token token = tokenRepository.findByMemberId(member.getId()).orElse(new Token());
        token.setMember(member);
        token.setRefreshToken(refreshToken);
        token.setExpirationDate(expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        tokenRepository.save(token);
    }

}
