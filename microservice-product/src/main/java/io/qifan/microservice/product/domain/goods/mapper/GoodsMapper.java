package io.qifan.microservice.product.domain.goods.mapper;

import io.qifan.microservice.api.product.request.GoodsCreateRequest;
import io.qifan.microservice.api.product.request.GoodsUpdateRequest;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.product.domain.goods.Goods;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoodsMapper {
    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);

    Goods createRequest2Entity(GoodsCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Goods updateEntityFromUpdateRequest(GoodsUpdateRequest request, @MappingTarget Goods entity);

    GoodsResponse entity2Response(Goods entity);
}
