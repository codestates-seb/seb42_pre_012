package com.pre012.server.member.dto;

import com.pre012.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpDto {
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$",
                 message = "비밀번호는 최소 4자 이상, 숫자와 영문자의 조합으로 이루어져야 합니다.")
        private String password;

        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        private String displayName;

        private String profileImage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyDto {
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$",
                message = "비밀번호는 최소 4자 이상, 숫자와 영문자의 조합으로 이루어져야 합니다.")
        private String password;
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
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
        private String profileImage; // 변경 가능성
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
        private String profileImage;
    }

}
