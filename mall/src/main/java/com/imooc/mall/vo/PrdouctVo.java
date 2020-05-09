package com.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Greated by Guo
 *
 * @date2020/5/9 23:32
 */
@Data
public class PrdouctVo {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImages;
    private Integer status;
    private BigDecimal price;

}
