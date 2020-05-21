package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IShippingService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Greated by Guo
 *
 * @date2020/5/20 17:09
 */
@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;
    @PostMapping("/shippings")
    public ResponseVo addShipping(@Valid @RequestBody ShippingForm shippingForm,
                                  HttpSession session){
        User use = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.addShipping(shippingForm,use.getId());
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo deleteShipping(@PathVariable Integer shippingId, HttpSession session){
        User use = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.deleteshipping(use.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo updateShipping(@PathVariable Integer shippingId,
                                     @Valid @RequestBody ShippingForm shippingForm,
                                     HttpSession session){
        User use = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.updateshipping(use.getId(),shippingId,shippingForm);
    }
    @GetMapping("/shippings")
    public ResponseVo<PageInfo> list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     HttpSession session){
        User use = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.listShipping(use.getId(), pageNum, pageSize);
    }

}
