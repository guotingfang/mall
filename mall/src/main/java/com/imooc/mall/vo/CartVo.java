package com.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车Vo
 * Greated by Guo
 *
 * @date2020/5/12 23:05
 */
@Data
public class CartVo {

    private Boolean selectedAll;
    private BigDecimal cartTotalPrice;
    private Integer cartTotalQuantity;

    private List<CartProductVo> cartProductVoList;

}
