package com.pre012.server.auth.repository;

import com.pre012.server.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMemberId(Long memberId);
    Optional<Token> findByRefreshTokenAndValid(String refreshToken, boolean valid);

    Optional<Token> findByRefreshTokenAndValidAndExpirationDateAfter(String refreshToken,
                                                                     boolean valid,
                                                                     LocalDateTime now);
}
