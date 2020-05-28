package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class IShippingServiceTest extends MallApplicationTests {
    @Autowired
    private IShippingService shippingService;

    private Integer shippingId;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Before
    public void shippingAdd() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("郭大哥");
        shippingForm.setReceiverPhone("012");
        shippingForm.setReceiverMobile("18888888888");
        shippingForm.setReceiverProvince("湖南");
        shippingForm.setReceiverCity("益阳市");
        shippingForm.setReceiverDistrict("桃江县");
        shippingForm.setReceiverAddress("修山镇");
        shippingForm.setReceiverZip("10034");
        ResponseVo<Map<String, Integer>> mapResponseVo = shippingService.addShipping(shippingForm, 19);
        log.info("result={}",mapResponseVo);
        this.shippingId= mapResponseVo.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),mapResponseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.deleteshipping(19, shippingId);
        log.info("result={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

     @Test
    public void update() {
         ShippingForm shippingForm = new ShippingForm();
         shippingForm.setReceiverName("郭爸爸");
         ResponseVo responseVo = shippingService.updateshipping(19, 27, shippingForm);
         log.info("result={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

     @Test
    public void list() {
         ResponseVo<PageInfo> responseVo = shippingService.listShipping(19, 1, 10);
         log.info("result={}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

}