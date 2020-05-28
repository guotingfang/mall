package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.vo.ResponseVo;

import java.util.Map;

/**
 * Greated by Guo
 *
 * @date2020/5/18 8:40
 */

public interface IShippingService {

    /**
     * 添加地址
     * @param shippingForm 表单验证类
     * @return
     */
    ResponseVo<Map<String, Integer>> addShipping(ShippingForm shippingForm, Integer userId);

    /**
     * 删除地址
     * @param shippingId
     * @return
     */
    ResponseVo deleteshipping(Integer uid, Integer shippingId);

     /**
     *修改地址
     * @param uid  用户ID
     * @param shippingId  地址ID
     * @param shippingForm   表单
     * @return
     */
    ResponseVo updateshipping(Integer uid, Integer shippingId, ShippingForm shippingForm);

    /**
     * 获取地址列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> listShipping(Integer uid, Integer pageNum, Integer pageSize);
}
