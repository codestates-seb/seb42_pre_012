package com.pre012.server.answer.enums;

import lombok.Getter;

public enum sortedBy {
    modified("newest"),
    created("oldest"),
    highest("default");

    @Getter
    private String sorted;

    sortedBy(String sorted) {
        this.sorted = sorted;
    }
}
