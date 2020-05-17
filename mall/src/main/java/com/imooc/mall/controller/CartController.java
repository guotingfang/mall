package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Greated by Guo
 *
 * @date2020/5/12 23:34
 */
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 获取购物车列表
     * @param session
     * @return
     */
    @GetMapping("/carts")
    public ResponseVo<CartVo> selectCartList(HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    /**
     * 添加购物车
     * @param cartAddForm
     * @param session
     * @return
     */
    @PostMapping("/carts")
   public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.addCart(user.getId(),cartAddForm);
    }

    /**
     * 修改/更新
     * @param cartUpdateForm
     * @param productId
     * @param session
     * @return
     */
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> updateCart(@Valid @RequestBody CartUpdateForm cartUpdateForm,
                                         @PathVariable Integer productId,
                                         HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(),productId,cartUpdateForm);
    }

    /**
     * 删除某商品
     * @param productId 商品ID
     * @param session
     * @return
     */
    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> deleteCart(@PathVariable Integer productId, HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    /**
     *  全部选中
     * @param session
     * @return
     */
    @PutMapping("/carts/selectAll")
    public  ResponseVo<CartVo> selectAll(HttpSession session){
            User user = (User) session.getAttribute(MallConst.CURRENT_USER);
            return cartService.selectAll(user.getId());
    }

    /**
     *全不选中
     * @param session
     * @return
     */
    @PutMapping("/carts/unSelectAll")
    public  ResponseVo<CartVo> unSelectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public  ResponseVo<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}
