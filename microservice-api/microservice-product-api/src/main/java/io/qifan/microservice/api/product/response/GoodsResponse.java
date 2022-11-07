package io.qifan.microservice.api.product.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsResponse {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private String name;


    private BigDecimal price;


    private String cover;

    private Integer stock;
}