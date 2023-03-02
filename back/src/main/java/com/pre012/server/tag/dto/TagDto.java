package com.pre012.server.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class TagDto {
    /**
     * post, patch dto
     */
    @Getter
    @AllArgsConstructor
    public static class Request {
        private int id;

        @NotBlank(message = "태그는 공백이면 안됩니다.")
        // test-tag (O), test tag (O), test- tag(X), test  tag(사이 공백 2개, X)
        @Pattern(regexp = "^([A-Za-z])([-|\\s]?[A-Za-z])*$",
                message = "태그 이름은 공백이거나, 공백으로 시작 또는 끝날 수 없습니다.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long tagId;
        private String tagName;
    }
}
