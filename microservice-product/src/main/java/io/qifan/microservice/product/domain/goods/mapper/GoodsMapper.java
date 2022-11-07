package io.qifan.microservice.product.domain.goods.mapper;

import io.qifan.microservice.api.product.request.GoodsCreateRequest;
import io.qifan.microservice.product.domain.goods.Goods;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoodsMapper {
    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);
    Goods createRequest2Entity(GoodsCreateRequest request);
}
