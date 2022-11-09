package io.qifan.microservice.order;

import io.qifan.microservice.api.product.GoodsFeignClient;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.order.domain.baseorder.BaseOrder;
import io.qifan.microservice.order.domain.baseorder.QBaseOrder;
import io.qifan.microservice.order.domain.baseorder.repository.BaseOrderRepository;
import io.qifan.microservice.order.domain.orderitem.BaseOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Slf4j
public class BaseOrderTest {
    @Autowired
    BaseOrderRepository baseOrderRepository;
    @Autowired
    GoodsFeignClient goodsFeignClient;

    /**
     * 场景：小明提交了一个订单，订单里面有两个商品。小明也可以查看自己的订单。
     */
    @Test
    public void saveTest() {
        GoodsResponse goods1 = new GoodsResponse();
        goods1.setId(40L);
        goods1.setCover("http://xxxx");
        goods1.setName("笔记本");
        goods1.setPrice(BigDecimal.valueOf(4000));
        goods1.setStock(10);

        GoodsResponse goods2 = new GoodsResponse();
        goods2.setId(41L);
        goods2.setCover("http://xxxx");
        goods2.setName("鼠标");
        goods2.setPrice(BigDecimal.valueOf(100));
        goods2.setStock(10);

        // 购买了一台笔记本
        BaseOrderItem baseOrderItem1 = new BaseOrderItem();
        baseOrderItem1.setGoods(goods1);
        baseOrderItem1.setCount(1);
        baseOrderItem1.setGoodsId(goods1.getId());

        // 购买了两个鼠标
        BaseOrderItem baseOrderItem2 = new BaseOrderItem();
        baseOrderItem2.setGoods(goods2);
        baseOrderItem2.setCount(2);
        baseOrderItem2.setGoodsId(goods2.getId());

        // 订单关联两个订单项
        BaseOrder baseOrder = new BaseOrder();
        // 初始化操作
        baseOrder.create();
        baseOrder.setAccountId(1L);
        baseOrder.setTotalAmount(BigDecimal.valueOf(4200));
        baseOrder.setItemList(List.of(baseOrderItem1, baseOrderItem2));

        baseOrderRepository.save(baseOrder);

        log.info("订单：{}，创建成功", baseOrder);
    }

    @Test
    void findTest() {
        Iterable<BaseOrder> result = baseOrderRepository.findAll(QBaseOrder
                .baseOrder
                .accountId
                .eq(1L));

        result.forEach(baseOrder -> {
            log.info(baseOrder.toString());
        });

    }
}
