package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Greated by Guo
 *
 * @date2020/5/25 11:40
 */
@Data
public class OrderCreaterForm {

    @NotNull
    private Integer shippingId;

}
