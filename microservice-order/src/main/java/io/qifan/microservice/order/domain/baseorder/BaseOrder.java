package io.qifan.microservice.order.domain.baseorder;

import io.qifan.microservice.common.jpa.BaseEntity;
import io.qifan.microservice.order.domain.baseorder.valueobject.OrderState;
import io.qifan.microservice.order.domain.orderitem.BaseOrderItem;
import io.qifan.microservice.order.infrastructure.converter.OrderStateConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "base_order")
@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class BaseOrder extends BaseEntity {

    @NotNull(message = "订单金额不能为空")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "账户id不能为空")
    private Long accountId;

    // 插入订单也会同时插入订单项，查询订单时也会自动join查出订单项。
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // 在订单项表生成外键base_order_id，关联本表的主键。
    // 订单项实体类不需要base_order_id这个字段，这边会自动生成这个字段。
    @JoinColumn(name = "base_order_id")
    @Size(min = 1, message = "购买商品不能为空")
    private List<BaseOrderItem> itemList;

    // 订单状态是java枚举类型，在数据库是int类型，插入到数据库时需要转换一下。
    @Convert(converter = OrderStateConverter.class)
    @NotNull(message = "订单状态不能为空")
    private OrderState orderState;

    // 创建时的初始化操作由实体类本身去操作，不需要外部去set
    public void create() {
        totalAmount = BigDecimal.ZERO;
        orderState = OrderState.UNPAID;
    }
}