package io.qifan.microservice.product.application;

import io.qifan.microservice.api.product.request.GoodsCreateRequest;
import io.qifan.microservice.api.product.request.GoodsQueryRequest;
import io.qifan.microservice.api.product.request.GoodsStockUpdateRequest;
import io.qifan.microservice.api.product.request.GoodsUpdateRequest;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.constants.ResultCode;
import io.qifan.microservice.common.exception.BusinessException;
import io.qifan.microservice.common.model.PageResult;
import io.qifan.microservice.common.model.QueryRequest;
import io.qifan.microservice.product.domain.goods.Goods;
import io.qifan.microservice.product.domain.goods.mapper.GoodsMapper;
import io.qifan.microservice.product.domain.goods.repository.GoodsRepository;
import io.qifan.microservice.product.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GoodsApplicationService {
    // @RequiredArgsConstructor + final 会自动注入。
    // 不需要@Autowired
    private final GoodsRepository goodsRepository;

    private final GoodsService goodsService;

    public Long create(GoodsCreateRequest createRequest) {
        // MapStruct，GoodsCreateRequest转成Goods(dto-> entity)
        Goods goods = GoodsMapper.INSTANCE.createRequest2Entity(createRequest);
        Goods saveResult = goodsRepository.save(goods);
        // saveResult 和 goods是同一个对象
        log.info("创建商品：{}", saveResult);
        return goods.getId();
    }

    public void update(GoodsUpdateRequest updateRequest, Long id) {
        // 这个goods肯定不为null，如果为null下面会抛出异常，就不会返回结果。
        Goods goods = goodsRepository
                // 先查找要更新的entity
                .findById(id)
                // 如果为空则抛出异常
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError));


        /*
         假设我要更新封面和价格，正常是这样写的。
         封面和价格不为空，则更新entity，然后更新数据库。
         if (StringUtils.isNotBlank(updateRequest.getCover())) {
             goods.setCover(updateRequest.getCover());
          }
         if (updateRequest.getPrice()!=null) {
            goods.setPrice(goods.getPrice());
          }
         goodsRepository.save(goods);
         显然上面这种写法很丑
        */

        // 使用mapstruct一行搞定，用更新请求去更新entity。
        GoodsMapper.INSTANCE.updateEntityFromUpdateRequest(updateRequest, goods);
        goodsRepository.save(goods);
        log.info("更新商品：{}", goods);
    }

    public GoodsResponse findById(Long id) {
        Goods goods = goodsRepository
                // 查找
                .findById(id)
                // 为空则抛出异常
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError));
        // 将entity转成response
        return GoodsMapper.INSTANCE.entity2Response(goods);
    }

    public List<GoodsResponse> findAllById(List<Long> ids) {
        // 根据多个id查询多个商品
        List<Goods> goodsList = goodsRepository.findAllById(ids);
        // 将List<Goods> -> List<GoodsResponse>
        return goodsList.stream()
                .map(GoodsMapper.INSTANCE::entity2Response)
                .collect(Collectors.toList());
    }


    public PageResult<GoodsResponse> query(QueryRequest<GoodsQueryRequest> queryRequest) {
        // 将查询请求dto映射为entity
        Page<Goods> queryResult = goodsService.query(GoodsMapper.INSTANCE.queryRequest2Entity(queryRequest));

        // 这边首先将，entity映射为response。再将page映射为PageResult（因为page里面包含了很多没必要的信息，需要过滤掉）。
        return PageResult.of(queryResult
                        .toList()
                        .stream()
                        .map(GoodsMapper.INSTANCE::entity2Response)
                        .collect(Collectors.toList()),
                queryResult.getTotalElements(),
                queryResult.getSize(),
                queryResult.getNumber());
    }

    public void increaseStock(List<GoodsStockUpdateRequest> goodsStockUpdateRequests) {
        goodsStockUpdateRequests.forEach(request -> {
            goodsService.increaseStock(request.getGoodsId(), request.getCount());
        });
    }

    public void decreaseStock(List<GoodsStockUpdateRequest> goodsStockUpdateRequests) {
        goodsStockUpdateRequests.forEach(request -> {
            goodsService.decreaseStock(request.getGoodsId(), request.getCount());
        });
    }
}
