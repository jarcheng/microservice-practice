package io.qifan.microservice.api.order.order.request;


import io.qifan.microservice.api.order.orderitem.request.BaseOrderItemCreateRequest;
import lombok.Data;

import java.util.List;

@Data

public class BaseOrderCreateRequest {

  // 实际上，这个字段前端不应该传过来，而是从当前线程去获取登录的id。
  private Long accountId;

  private List<BaseOrderItemCreateRequest> itemList;
}
