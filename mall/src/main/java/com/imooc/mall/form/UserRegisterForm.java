package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Greated by Guo
 *
 * @date2020/5/6 17:14
 */
@Data
public class UserRegisterForm {

    //@NotEmpty 主要用于判断集合是否为空
    //@NotBlank 主要用于String的判断，会判断空格
    //@NotNull 主要用于 INT 型的判断
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;


}
