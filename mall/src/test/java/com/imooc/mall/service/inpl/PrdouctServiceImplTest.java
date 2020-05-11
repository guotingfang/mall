package com.imooc.mall.service.inpl;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.PrdouctDetailVo;
import com.imooc.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PrdouctServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> list = productService.list(100002, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());
    }
    @Test
    public void detail(){
        ResponseVo<PrdouctDetailVo> responseVo = productService.detail(27);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}