package io.qifan.microservice.product.domain.goods;

import io.qifan.microservice.common.jpa.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "goods", indexes = @Index(columnList = "name", unique = true))
@Data
public class Goods extends BaseEntity {

    /**
     * null，""都会报错
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;
    /**
     * 价格要用BigDecimal
     * 最大范围+-99999999.99
     */
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotBlank(message = "封面不能为空")
    private String cover;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;
}
