package io.qifan.microservice.order.domain.baseorder.service;

import io.qifan.microservice.api.product.GoodsFeignClient;
import io.qifan.microservice.api.product.request.GoodsStockUpdateRequest;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.order.domain.baseorder.BaseOrder;
import io.qifan.microservice.order.domain.orderitem.BaseOrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseOrderService {
    private final GoodsFeignClient goodsFeignClient;


    public void computePrice(BaseOrder baseOrder) {
        List<Long> goodsIds = baseOrder
                .getItemList()
                .stream()
                .map(BaseOrderItem::getGoodsId)
                .collect(Collectors.toList());
        // 调用商品服务查询信息
        List<GoodsResponse> result = goodsFeignClient.findAllById(goodsIds).getResult();
        // 计算价格
        for (int i = 0; i < result.size(); i++) {
            GoodsResponse goods = result.get(i);
            BaseOrderItem baseOrderItem = baseOrder.getItemList().get(i);
            // 设置商品详情
            baseOrderItem.setGoods(goods);
            // 计算价格
            // 商品单价*数量
            BigDecimal itemPrice = goods.getPrice().multiply(BigDecimal.valueOf(baseOrderItem.getCount()));
            // 累加
            baseOrder.setTotalAmount(baseOrder.getTotalAmount().add(itemPrice));
        }
        log.info("总价格：{}", baseOrder.getTotalAmount());
        List<GoodsStockUpdateRequest> goodsStockUpdateRequests = baseOrder.getItemList()
                .stream()
                // 得到要扣减的商品id和扣除数量
                .map(item -> new GoodsStockUpdateRequest(item.getGoodsId(), item.getCount()))
                .collect(Collectors.toList());
        // 调用商品服务扣减库存
        goodsFeignClient.decreaseStock(goodsStockUpdateRequests);
        log.info("计算价格，并扣减库存。商品购买详情：{}", baseOrder.getItemList());
    }
}
