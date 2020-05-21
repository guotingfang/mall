package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Greated by Guo
 *
 * @date2020/5/17 23:14
 */
@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String receiverMobile;
    @NotBlank
    private String receiverProvince;
    @NotBlank
    private String receiverCity;
    @NotBlank
    private String receiverDistrict;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    private String receiverZip;
}
