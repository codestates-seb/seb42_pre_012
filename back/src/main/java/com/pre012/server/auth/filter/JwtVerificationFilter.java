package com.pre012.server.auth.filter;

import com.pre012.server.auth.util.CustomAuthorityUtils;
import com.pre012.server.auth.util.JWTTokenizer;
import com.pre012.server.common.utils.TypeCastingUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JWTTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    public JwtVerificationFilter(JWTTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> claims = verifyJws(request, response);
        if (claims != null) { // 토큰 검증, claims 파싱이 잘 되면
            setAuthenticationToContext(claims);
        }
        filterChain.doFilter(request, response);
    }

    //  "Header - Authorization 없거나  'Bearer' 로 시작하지 않을 때"
    //  JWT 자격증명이 필요하지 않은 리소스에 대한 요청이라고 판단, 다음 필터로 처리 넘어가도록
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    // Jws 가져와서 getClaims() 처리
    private Map<String, Object> verifyJws(HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Jws<Claims> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey, response);
        return claims != null ? claims.getBody() : null;
    }

    // SecurityContextHolder 에 인증정보 저장
    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<String> roles = TypeCastingUtils.objToList(claims.get("roles"), String.class);
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
