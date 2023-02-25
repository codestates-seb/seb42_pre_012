package com.pre012.server.auth.controller;

import com.pre012.server.auth.dto.AuthDto.LogoutDto;
import com.pre012.server.auth.dto.AuthDto.ReissueDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@Valid @RequestBody LogoutDto logoutDto) {
        // 토큰이랑 같이 구현 예정, Entity 추가 필요 예상,,
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<HttpStatus> reissue(@Valid @RequestBody ReissueDto reissueDto) {
        // Entity 추가 필요 예상,,
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
