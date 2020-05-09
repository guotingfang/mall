package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Greated by Guo
 *
 * @date2020/5/6 17:14
 */
@Data
public class UserLoginForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;


}
