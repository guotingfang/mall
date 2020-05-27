package com.imooc.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Greated by Guo
 *
 * @date2020/5/22 12:14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  PaymentTypeEnum {

    PAY_ONLINE(1,"在线支付"),
    ;
    private Integer code;
    private String desc;


}
