package io.qifan.microservice.order.domain.baseorder.valueobject;

import io.qifan.microservice.common.constants.BaseEnum;
import io.qifan.microservice.common.constants.ResultCode;
import io.qifan.microservice.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum OrderState implements BaseEnum {
    UNPAID(0, "待支付"),
    PAID(1, "已支付"),
    CANCELED(-1, "已取消");


    final Integer code;

    final String name;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    // 根据code找到枚举对象
    public static OrderState of(Integer code) {
        return Arrays.stream(OrderState.values())
                .filter(orderState -> orderState.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError));
    }
}
