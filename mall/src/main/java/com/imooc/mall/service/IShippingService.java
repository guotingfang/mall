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




//    /**
//     * 获取默认的地址
//     * @param uid
//     * @return
//     */
//    ResponseVo<Shipping> selectByStatus(Integer uid);
//
//    /**
//     * 修改地址状态 0 默认 1 启用（非默认） 2 停用（软删除）
//     * @param uid
//     * @param shippingId
//     * @param status
//     * @return
//     */
//    ResponseVo updateStatusByShippingId(Integer uid, Integer shippingId, Integer status);
}
