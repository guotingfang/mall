package com.imooc.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Greated by Guo
 *
 * @date2020/5/12 23:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductVo {

    private Integer productId;
    /**
     * 购买数量
     */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    /**
     * 等于 quantity*productPrice
     */
    private BigDecimal productTotalPrice;
    private Integer productStock;
    /**
     * 商品是否被选中
     */
    private Boolean productSelected;

}
