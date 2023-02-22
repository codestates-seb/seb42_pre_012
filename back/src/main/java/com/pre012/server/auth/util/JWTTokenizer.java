package com.pre012.server.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

/*
 * JWT 생성 담당
 */
@Component
public class JWTTokenizer {
    
    
    @Getter
    @Value("${jwt.key}")
    private String secretKey;
    // 1) JWT 생성 및 검증 시 사용되는 Secret Key 정보
    
    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;
    // 2) Access Token 유효 시간

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;
    // 3) Refesh Token 유효 시간
    
    // Secret Key byte[] -> Base64 인코딩
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // AccessToken 생성
    public String generateAccessToken(Map<String, Object> claims, 
                                      String subject, 
                                      Date ExpirationDate, 
                                      String encodedSecretKey) 
    {
        Key secretKey = getKeyFromBase64EncodedKey(encodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)   // JWT에 포함 시킬 정보
                .setSubject(subject) // JWT 제목
                .setIssuedAt(Calendar.getInstance().getTime()) // 생성 일시
                .setExpiration(ExpirationDate)                 // 만료 일시
                .signWith(secretKey)
                .compact();
    }

    // RefreshToken 생성
    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
        
    // JWT 서명에 사용할 SecretKey 생성
    private Key getKeyFromBase64EncodedKey(String encodedSecretKey) {
        // 1. Base64 -> byte[] 디코딩
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecretKey);
        // 2. 적절한 HMAC 알고리즘을 적용한 SecretKey
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // claim 파싱
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Jws<Claims> claims = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(jws);
        return claims;
    }

    // JWT 만료 일시 지정
    public Date getTokenExpirationDate(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expirationDate = calendar.getTime();

        return expirationDate;
    }
}