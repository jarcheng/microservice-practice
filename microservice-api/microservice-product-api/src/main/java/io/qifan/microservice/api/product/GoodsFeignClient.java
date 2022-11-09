package io.qifan.microservice.api.product;

import io.qifan.microservice.api.product.request.GoodsStockUpdateRequest;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.model.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "goods", url = "http://microservice-product:9000", path = "goods")
public interface GoodsFeignClient {
    @PostMapping("findAllById")
    R<List<GoodsResponse>> findAllById(@RequestBody List<Long> ids);

    @PostMapping("stock/decrease")
    R<String> decreaseStock(@RequestBody List<GoodsStockUpdateRequest> goodsStockUpdateRequests);


    @PostMapping("stock/increase")
    R<String> increaseStock(@RequestBody List<GoodsStockUpdateRequest> goodsStockUpdateRequests);
}