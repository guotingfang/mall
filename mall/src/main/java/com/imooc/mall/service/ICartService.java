package com.imooc.mall.service;

import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * Greated by Guo
 *
 * @date2020/5/13 15:42
 */

public interface ICartService {

    /**
     * 往购物车添加商品
     * @param cartform
     * @return
     */
    ResponseVo<CartVo> addCart(Integer uid, CartAddForm cartform);

    /**
     *
     * @param uid
     * @return
     */
    ResponseVo<CartVo> list(Integer uid);

    /**
     * 更新&删除
     * @param uid
     * @param cartUpdateForm 非必填，
     * @return
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);


    /**
     * 删除
     * @param uid
     * @param productId
     * @return
     */
    ResponseVo<CartVo> delete(Integer uid, Integer productId);


    /**
     * 购物车商品全选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> selectAll(Integer uid);


    /**
     * 全部不选中
     * @param uid
     * @return
     */
    ResponseVo<CartVo> unSelectAll (Integer uid);

    /**
     * 获取购物中所有商品数量总和
     * @param uid
     * @return
     */
    ResponseVo<Integer> sum(Integer uid);

    /**
     * 获取购物车所有商品
     * @param uid
     * @return
     */
    List<Cart> listForCart(Integer uid);
}
