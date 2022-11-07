package io.qifan.microservice.product.domain.goods.service;

import com.querydsl.core.BooleanBuilder;
import io.qifan.microservice.common.model.QueryRequest;
import io.qifan.microservice.product.domain.goods.Goods;
import io.qifan.microservice.product.domain.goods.QGoods;
import io.qifan.microservice.product.domain.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public Page<Goods> query(QueryRequest<Goods> queryRequest) {
        // 动态的过滤条件
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // 如果存在商品名称则添加这个条件
        if (StringUtils.isNotBlank(queryRequest.getQuery().getName())) {
            // 使用queryDsl（和mybatis-plus类似）
            booleanBuilder.and(QGoods
                    .goods
                    .name
                    .like("%" + queryRequest.getQuery().getName() + "%"));
        }
        // 查询
        return goodsRepository.findAll(booleanBuilder, queryRequest.toPage());
    }
}
