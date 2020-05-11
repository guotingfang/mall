package com.imooc.mall.enums;

import lombok.Getter;

/**
 * Greated by Guo
 *
 * @date2020/5/11 11:42
 */
@Getter
public enum ProductStatusEnum {

    ON_SALE(1,"在售"),
    OFF_SALE(2,"下架"),
    DELETE(3,"删除"),
    ;

    private Integer code;
    private String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
