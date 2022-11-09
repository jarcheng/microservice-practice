package io.qifan.microservice.order.domain.orderitem;

import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.jpa.BaseEntity;
import io.qifan.microservice.order.infrastructure.converter.GoodsConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "base_order_item")
@Entity
public class BaseOrderItem extends BaseEntity {

    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    @NotNull(message = "数量不能为空")
    private Integer count;


    // 商品是java对象，在数据库是json类型，插入到数据库时需要转换一下。
    @Convert(converter = GoodsConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private GoodsResponse goods;
}