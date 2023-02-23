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
        private MemberInfo member;
        private MemberActivity activity;
    }

    @Getter
    @AllArgsConstructor
    public static class MemberInfo {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
        private String createdAt;
        private String modifiedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class MemberActivity {
        private int questionCnt;
        private int answerCnt;
    }

    @Getter
    @AllArgsConstructor
    public static class MemberSimpleInfo {
        private Long memberId;
        private String email;
        private String displayName;
        private String profileImagePath;
    }

}
