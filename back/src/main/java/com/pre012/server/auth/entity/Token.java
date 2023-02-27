package com.pre012.server.auth.entity;

import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "member_token")
public class Token {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean valid = true;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
