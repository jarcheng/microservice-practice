package io.qifan.microservice.api.order.orderitem.request;

import lombok.Data;

@Data
public class BaseOrderItemCreateRequest {

  private Long goodsId;

  private Integer count;
}
