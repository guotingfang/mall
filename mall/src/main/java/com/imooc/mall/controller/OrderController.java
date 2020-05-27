package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.OrderCreaterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IOrderService;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Greated by Guo
 *
 * @date2020/5/25 11:27
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreaterForm form,
                                      HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return  orderService.create(user.getId(), form.getShippingId());
    }
    @GetMapping("/orders")
    public ResponseVo<PageInfo> create(@RequestParam("pageSize") Integer pageSize,
                                       @RequestParam("pageNum") Integer pageNum,
                                       HttpSession session){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable("orderNo") Long orderNo,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable("orderNo") Long orderNo,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }
}
