package io.qifan.microservice.order.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.constants.ResultCode;
import io.qifan.microservice.common.exception.BusinessException;
import io.qifan.microservice.order.infrastructure.uitls.JacksonObject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GoodsConverter implements AttributeConverter<GoodsResponse, String> {

    /**
     * 数据库里面product这个字段是string类型，而java里面这个字段是GoodsResponse
     * GoodsResponse序列化成json字符串
     *
     * @param attribute 实体类需要转换的字段
     * @return json字符串
     */
    @Override
    public String convertToDatabaseColumn(GoodsResponse attribute) {
        try {
            return JacksonObject.objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ResultCode.SystemError, "商品详情转json异常");
        }
    }

    /**
     * 数据库里面product这个字段是string类型，而java里面这个字段是GoodsResponse
     * json字符串反序列化成GoodsResponse
     *
     * @param dbData 数据库里面的json字符串
     * @return GoodsResponse对象
     */
    @Override
    public GoodsResponse convertToEntityAttribute(String dbData) {
        try {
            return JacksonObject.objectMapper.readValue(dbData, GoodsResponse.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ResultCode.SystemError, "json转商品详情异常");
        }
    }
}
