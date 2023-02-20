package com.pre012.server.member.enums;

import lombok.Getter;

public enum LikeType {
    LIKE("좋아요"),
    UNLIKE("싫어요");

    @Getter
    private String type;

    LikeType(String type) {
        this.type = type;
    }
}
