package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class IOrderServiceTest extends MallApplicationTests {
    @Autowired
    private IOrderService orderService;

    private Integer uid = 19;
    private Integer shippingId = 5;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("result={}", gson.toJson(responseVo));
    }

    @Test
    public void list(){
        ResponseVo<PageInfo> responseVo = orderService.list(uid, 1, 10);
        log.info("reultf={}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());

    }
    @Test
    public void deatil(){
        ResponseVo<OrderVo> responseVo = orderService.detail(uid, Long.valueOf("1590154886494"));
        log.info("resut= {}", gson.toJson(responseVo));
    }
    @Test
    public void cancel(){
        ResponseVo responseVo = orderService.cancel(uid, Long.valueOf("1590154886494"));
        log.info("resut= {}", gson.toJson(responseVo));
    }

}
