package com.pre012.server.auth.controller;

import com.pre012.server.auth.dto.AuthDto.LogoutDto;
import com.pre012.server.auth.dto.AuthDto.ReissueDto;
import com.pre012.server.auth.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@Valid @RequestBody LogoutDto logoutDto) {
        // 토큰이랑 같이 구현 예정, Entity 추가 필요 예상,,
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<String> reissue(@Valid @RequestBody ReissueDto reissueDto) {
        Map<String, String> tokens = tokenService.reissueAccessToken(reissueDto.getRefreshToken());
        return ResponseEntity.ok().header("Authorization", "Bearer " + tokens.get("accessToken"))
                                  .header("RefreshToken", tokens.get("refreshToken"))
                                  .body(null);
    }

}
