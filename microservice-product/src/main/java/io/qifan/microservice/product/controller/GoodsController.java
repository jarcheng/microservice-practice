package io.qifan.microservice.product.controller;

import io.qifan.microservice.api.product.request.GoodsCreateRequest;
import io.qifan.microservice.api.product.request.GoodsQueryRequest;
import io.qifan.microservice.api.product.request.GoodsUpdateRequest;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.model.PageResult;
import io.qifan.microservice.common.model.QueryRequest;
import io.qifan.microservice.common.model.R;
import io.qifan.microservice.product.application.GoodsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsApplicationService goodsApplicationService;

    @GetMapping("findById/{id}")
    public R<GoodsResponse> findById(@PathVariable Long id) {
        return R.ok(goodsApplicationService.findById(id));
    }


    @PostMapping("create")
    public R<Long> create(@RequestBody @Valid GoodsCreateRequest createRequest) {
        return R.ok(goodsApplicationService.create(createRequest));
    }

    @PostMapping("update/{id}")
    public R<String> update(@RequestBody GoodsUpdateRequest updateRequest, @PathVariable Long id) {
        goodsApplicationService.update(updateRequest, id);
        return R.ok();
    }


    @PostMapping("findAllById")
    public R<List<GoodsResponse>> findAllById(@RequestBody List<Long> ids) {
        return R.ok(goodsApplicationService.findAllById(ids));
    }

    @PostMapping("query")
    public R<PageResult<GoodsResponse>> query(@RequestBody QueryRequest<GoodsQueryRequest> queryRequest) {
        return R.ok(goodsApplicationService.query(queryRequest));
    }
}
