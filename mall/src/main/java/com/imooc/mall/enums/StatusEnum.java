package com.imooc.mall.enums;

import lombok.Getter;

/**
 * Greated by Guo
 *
 * @date2020/5/8 21:47
 */
@Getter
public enum StatusEnum {

    ENABLE(1,"启用"),
    DISABLE(0,"停用"),
    ;

    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
