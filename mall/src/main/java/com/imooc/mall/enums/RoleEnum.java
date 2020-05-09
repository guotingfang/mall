package com.imooc.mall.enums;

import lombok.Getter;

/**
 * Greated by Guo
 *
 * @date2020/5/5 11:20
 */
@Getter
public enum  RoleEnum {

    ADMAIN(0),
    CUSTOMER(1),
    ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }

}

