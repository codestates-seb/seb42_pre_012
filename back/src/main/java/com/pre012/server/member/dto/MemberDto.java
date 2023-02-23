package com.pre012.server.member.dto;

import com.pre012.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpDto {
        private String email;
        private String password;
        private String displayName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileResponseDto {
        private ProfileMember member;
        private ProfileActivity activity;
    }

    @Getter
    @AllArgsConstructor
    public static class ProfileMember {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
        private String createdAt;
        private String modifiedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class ProfileActivity {
        private int questionCnt;
        private int answerCnt;
    }

}
