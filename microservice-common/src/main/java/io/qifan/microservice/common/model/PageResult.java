package io.qifan.microservice.common.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Long total;

    private Integer totalPages;
    private Integer pageSize;
    private Integer page;
    private List<T> list;

    public PageResult(List<T> list, Long total, Integer pageSize, Integer page) {
        this.list = list;
        this.total = total;
        this.pageSize = pageSize;
        this.page = page;
    }

    public static <T> PageResult<T> of(List<T> list, Long total, Integer pageSize, Integer pageNumber) {
        return new PageResult<>(list, total, pageSize, pageNumber);
    }
}
