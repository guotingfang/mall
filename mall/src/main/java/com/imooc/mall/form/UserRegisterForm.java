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
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;


}
