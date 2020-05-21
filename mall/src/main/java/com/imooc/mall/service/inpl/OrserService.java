package com.imooc.mall.service.inpl;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.service.IOrderService;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

/**
 * Greated by Guo
 *
 * @date2020/5/21 10:34
 */
@Service
public class OrserService implements IOrderService {


    /**
     * @param uid
     * @param shippingId
     * @return
     */
    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //生成订单号
        //通过商品ID获取商品信息
        //通过用户ID

        return null;
    }

    @Override
    public ResponseVo<PageInfo> listOrder(Integer uid, Integer shippingId) {
        return null;
    }

    @Override
    public ResponseVo<OrderVo> dateOrder(Integer uid, Integer orderNo) {
        return null;
    }

    @Override
    public ResponseVo cancelOrder(Integer uid, Integer orderNo) {
        return null;
    }
}
