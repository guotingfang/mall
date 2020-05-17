package com.imooc.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Greated by Guo
 *
 * @date2020/5/13 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

}
