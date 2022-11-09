package io.qifan.microservice.order.domain.orderitem.mapper;

import io.qifan.microservice.api.order.orderitem.request.BaseOrderItemCreateRequest;
import io.qifan.microservice.api.order.orderitem.response.BaseOrderItemResponse;
import io.qifan.microservice.order.domain.orderitem.BaseOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseOrderItemMapper {
  BaseOrderItemMapper INSTANCE = Mappers.getMapper(BaseOrderItemMapper.class);

  BaseOrderItem createRequest2Entity(BaseOrderItemCreateRequest request);

  BaseOrderItemResponse entity2Response(BaseOrderItem entity);
}
