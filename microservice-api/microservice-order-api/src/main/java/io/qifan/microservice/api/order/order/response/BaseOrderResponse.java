package io.qifan.microservice.api.order.order.response;


import io.qifan.microservice.api.order.orderitem.response.BaseOrderItemResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BaseOrderResponse  {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BigDecimal totalAmount;

    private Long accountId;

    private Integer orderState;


    private List<BaseOrderItemResponse> itemList;
}
