package com.imooc.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Greated by Guo
 *订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭
 * @date2020/5/22 12:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatusEnum {

    CANCELED(0,"已取消"),
    NO_PAY(10,"未付款"),
    PAID(20,"已付款"),
    SHIPPED(40,"已发货"),
    TRADE_SUCCESS(50,"交易成功"),
    TRADE_DOWN(60,"交易关闭"),
    ;
    private Integer code;
    private String desc;
}
