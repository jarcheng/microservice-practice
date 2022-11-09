package io.qifan.microservice.api.order.orderitem.response;


import io.qifan.microservice.api.product.response.GoodsResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseOrderItemResponse  {
  private Long id;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
  private Long goodsId;


  private Integer count;


  private GoodsResponse goods;
}
