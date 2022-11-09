package io.qifan.microservice.api.order.order.request;

import lombok.Data;

@Data
public class BaseOrderQueryRequest {

    private Integer orderState;

    private Long accountId;
}
