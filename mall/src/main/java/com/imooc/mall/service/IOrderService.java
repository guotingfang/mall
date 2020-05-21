package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;

/**
 * Greated by Guo
 *
 * @date2020/5/21 10:33
 */

public interface IOrderService {
    /**
     *
     * @param shippingId
     * @param uid
     * @return
     */
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    ResponseVo<PageInfo> listOrder(Integer uid, Integer shippingId);

    ResponseVo<OrderVo> dateOrder(Integer uid, Integer orderNo);

    ResponseVo cancelOrder(Integer uid, Integer orderNo);
}
