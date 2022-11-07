package io.qifan.microservice.common.model;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Data
public class QueryRequest<T> {
    // 查询条件
    private T query;
    // 一页显示多少数据
    private Integer pageSize;
    // 从第几页开始查找
    private Integer page;
    private Map<String, String> sorts;

    public Pageable toPage() {
        return PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}