package io.qifan.microservice.order.controller;

import com.querydsl.core.BooleanBuilder;
import io.qifan.microservice.api.order.order.request.BaseOrderCreateRequest;
import io.qifan.microservice.api.order.order.request.BaseOrderQueryRequest;
import io.qifan.microservice.api.order.order.response.BaseOrderResponse;
import io.qifan.microservice.common.model.PageResult;
import io.qifan.microservice.common.model.QueryRequest;
import io.qifan.microservice.common.model.R;
import io.qifan.microservice.order.application.OrderApplicationService;
import io.qifan.microservice.order.domain.baseorder.BaseOrder;
import io.qifan.microservice.order.domain.baseorder.QBaseOrder;
import io.qifan.microservice.order.domain.baseorder.mapper.BaseOrderMapper;
import io.qifan.microservice.order.domain.baseorder.repository.BaseOrderRepository;
import io.qifan.microservice.order.domain.baseorder.valueobject.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    private final BaseOrderRepository baseOrderRepository;

    @PostMapping("create")
    public R<Long> create(@RequestBody BaseOrderCreateRequest createRequest) {
        return R.ok(orderApplicationService.create(createRequest));
    }

    @PostMapping("query")
    public R<PageResult<BaseOrderResponse>> query(@RequestBody QueryRequest<BaseOrderQueryRequest> queryRequest) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (queryRequest.getQuery().getAccountId() != null) {
            booleanBuilder.and(QBaseOrder.baseOrder.accountId.eq(queryRequest.getQuery().getAccountId()));
        }
        if (queryRequest.getQuery().getOrderState() != null) {
            booleanBuilder.and(QBaseOrder.baseOrder.orderState.eq(OrderState.of(queryRequest.getQuery().getOrderState())));

        }
        Page<BaseOrder> all = baseOrderRepository.findAll(booleanBuilder, queryRequest.toPage());
        return R.ok(PageResult.of(all
                        .getContent()
                        .stream()
                        .map(BaseOrderMapper.INSTANCE::entity2Response)
                        .collect(Collectors.toList()),
                all.getTotalElements(),
                all.getSize(),
                all.getNumber()));
    }
}
