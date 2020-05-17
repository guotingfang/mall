package com.imooc.mall.vo;

import lombok.Data;

import java.util.Date;

/**
 * Greated by Guo
 *
 * @date2020/5/17 23:18
 */
@Data
public class ShippingVo {
    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Date createTime;

    private Date updateTime;

}
