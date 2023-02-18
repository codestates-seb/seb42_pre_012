package com.pre012.server.member.enums;

import lombok.Getter;

public enum MemberStatus {

    MEMBER_ACTIVE("활동 회원"),
    MEMBER_SLEEPED("휴면 회원"),
    MEMBER_DELETED("탈퇴 회원");
    
    @Getter
    private String status;
    
    MemberStatus(String status) {
        this.status = status;
    }
    
}
