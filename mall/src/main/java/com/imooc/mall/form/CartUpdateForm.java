package com.imooc.mall.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Greated by Guo
 *
 * @date2020/5/15 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateForm {

    private Integer quantity;//非必填

    private Boolean selected;//非必填

}
