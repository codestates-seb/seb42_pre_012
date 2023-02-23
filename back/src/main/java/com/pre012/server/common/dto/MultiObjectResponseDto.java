package com.pre012.server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MultiObjectResponseDto<T, E> {
    private T data;
    private PageInfo pageInfo;

    public MultiObjectResponseDto(T data, Page<E> page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @AllArgsConstructor
    @Getter
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}