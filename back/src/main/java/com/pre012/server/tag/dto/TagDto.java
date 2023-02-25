package com.pre012.server.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TagDto {
    /**
     * post, patch dto
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        private int id; // 그냥 순서대로 보내주는 id 값
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long tagId;
        private String tagName;
    }
}
