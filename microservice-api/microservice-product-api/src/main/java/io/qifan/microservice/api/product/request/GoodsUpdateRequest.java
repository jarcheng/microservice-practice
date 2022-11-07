package io.qifan.microservice.api.product.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsUpdateRequest {

    private String name;


    private BigDecimal price;


    private String cover;


    private Integer stock;
}
