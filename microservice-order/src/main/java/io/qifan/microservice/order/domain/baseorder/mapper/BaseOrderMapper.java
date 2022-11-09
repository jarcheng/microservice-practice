package io.qifan.microservice.order.domain.baseorder.mapper;


import io.qifan.microservice.api.order.order.request.BaseOrderCreateRequest;
import io.qifan.microservice.api.order.order.response.BaseOrderResponse;
import io.qifan.microservice.order.domain.baseorder.BaseOrder;
import io.qifan.microservice.order.domain.orderitem.mapper.BaseOrderItemMapper;
import io.qifan.microservice.order.infrastructure.converter.OrderStateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        uses = {BaseOrderItemMapper.class, OrderStateConverter.class}
)
public interface BaseOrderMapper {
    BaseOrderMapper INSTANCE = Mappers.getMapper(BaseOrderMapper.class);

    BaseOrder createRequest2Entity(BaseOrderCreateRequest request);

    BaseOrderResponse entity2Response(BaseOrder entity);
}
