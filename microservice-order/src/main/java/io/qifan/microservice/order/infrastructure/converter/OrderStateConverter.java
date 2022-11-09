package io.qifan.microservice.order.infrastructure.converter;


import io.qifan.microservice.order.domain.baseorder.valueobject.OrderState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderStateConverter implements AttributeConverter<OrderState, Integer> {

    /**
     * 这个映射在 实体类 -> 数据库和实体类 -> Response时都可以用。
     * 订单状态枚举类型-> 整数类型
     */
    @Override
    public Integer convertToDatabaseColumn(OrderState orderState) {
        return orderState.getCode();
    }

    /**
     * 这个映射在 数据库 -> 实体类和Request -> 实体类时都可以用。
     * 整数类型 -> 订单状态枚举类型
     */
    @Override
    public OrderState convertToEntityAttribute(Integer integer) {
        return OrderState.of(integer);
    }
}
