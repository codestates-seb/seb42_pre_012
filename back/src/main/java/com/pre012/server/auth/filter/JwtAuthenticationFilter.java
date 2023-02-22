package com.pre012.server.auth.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

import static com.pre012.server.auth.dto.AuthDto.Login;

import java.io.IOException;


/*
 * 로그인 인증 담당 Security Filter
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    private final AuthenticationManager authenticationManager; // 인증 처리 담당 Manager DI

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows // 명시적인 예외 처리 생략 (IOException, StreamReadException, DatabindException 등...)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response) 
    {
        // 1. ServletInputStream -> LoginDto 객체로 Deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        Login loginDto = objectMapper.readValue(request.getInputStream(), Login.class);

        // 2. 인증 전 유저 정보 저장
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        
        // 3. Manager에게 인증 처리 위임
        return authenticationManager.authenticate(authenticationToken);
    }

    // 인증 성공 시 호출
    @Override
    protected void successfulAuthentication(HttpServletRequest request, 
                                            HttpServletResponse response, 
                                            FilterChain chain, 
                                            Authentication authResult) throws ServletException, IOException
    {
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

}
