package io.qifan.microservice.product;

import io.qifan.microservice.product.domain.goods.Goods;
import io.qifan.microservice.product.domain.goods.QGoods;
import io.qifan.microservice.product.domain.goods.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class GoodsTest {
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void createTest() {
        Goods goods = new Goods();
        goods.setCover("https://www.jarcheng.top/blog/qifan_logo.jpg");
        goods.setName("测试商品");
        goods.setPrice(BigDecimal.TEN);
        goods.setStock(100);
        Goods save = goodsRepository.save(goods);
        log.info("插入商品：{}", save);
    }

    @Test
    public void findByName() {
        goodsRepository.findOne(QGoods.goods.name.eq("测试商品"))
                .ifPresent((Goods goods) -> {
                    log.info(goods.toString());
                });
    }
}
