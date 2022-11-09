package io.qifan.microservice.api.product.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GoodsStockUpdateRequest {
    @NotNull(message = "商品id不能为空")
    Long goodsId;

    @NotNull(message = "购买数量不能为空")
    Integer count;
}
