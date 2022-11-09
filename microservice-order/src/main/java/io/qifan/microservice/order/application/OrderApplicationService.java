package io.qifan.microservice.order.application;

import io.qifan.microservice.api.order.order.request.BaseOrderCreateRequest;
import io.qifan.microservice.order.domain.baseorder.BaseOrder;
import io.qifan.microservice.order.domain.baseorder.mapper.BaseOrderMapper;
import io.qifan.microservice.order.domain.baseorder.repository.BaseOrderRepository;
import io.qifan.microservice.order.domain.baseorder.service.BaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderApplicationService {
    private final BaseOrderService baseOrderService;
    private final BaseOrderRepository baseOrderRepository;

    public Long create(BaseOrderCreateRequest createRequest) {
        BaseOrder baseOrder = BaseOrderMapper.INSTANCE.createRequest2Entity(createRequest);
        // 订单初始话操作
        baseOrder.create();
        // 价格计算，扣减库存
        baseOrderService.computePrice(baseOrder);
        // 插入订单到数据库，同时插入订单项到数据库
        baseOrderRepository.save(baseOrder);
        log.info("创建订单：{}", baseOrder);
        return baseOrder.getId();

    }
}