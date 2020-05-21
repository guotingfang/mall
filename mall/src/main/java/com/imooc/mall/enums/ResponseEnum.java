package com.imooc.mall.enums;

import lombok.Getter;

/**
 * Greated by Guo
 *
 * @date2020/5/6 9:28
 */

@Getter
public enum ResponseEnum {

    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名已存在"),
    PARAM_ERROR(3,"参数错误"),
    EMAIL_EXIST(4,"邮箱已存在"),
    NEED_LOGIN(10,"用户未登录,请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或密码错误"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"该商品已下架或删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_ERROR(14,"库存不足"),
    CART_PRODUCT_NOT_EXIST(15,"购物车里没有该商品"),
    ADDRESS_ADD_ERROR(16,"新建地址成功"),
    ADDRESS_ADD_SUCCESS(17,"新建地址失败"),
    DELETE_SHIPPING_SUCCESS(18,"删除收货地址成功"),
    DELETE_SHIPPING_FAIL(19,"删除收货地址失败"),
    ;

    private Integer code;
    private String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
