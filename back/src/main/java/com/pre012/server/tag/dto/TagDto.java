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
        private int id;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long tagId;
        private String tagName;
    }
}
