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
     *新建订单
     * @param shippingId
     * @param uid
     * @return
     */
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    /**
     * 获取订单LIST
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    /**
     * 删除订单
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo cancel(Integer uid, Long orderNo);

    /**
     * 修改订单状态
     * @param orderNo
     */
    void paid(Long orderNo);
}
