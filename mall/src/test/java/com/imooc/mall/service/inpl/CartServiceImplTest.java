package com.imooc.mall.service.inpl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class CartServiceImplTest extends MallApplicationTests {
    @Autowired
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    private ICartService cartService;

    private Integer prdouctId = 27;

    private Integer uid = 19;

    @Test
    public void addCart() {
        log.info("新增购物车...");
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(prdouctId);
        cartAddForm.setSelected(true);
        ResponseVo<CartVo> cartVoResponseVo = cartService.addCart(uid, cartAddForm);
        log.info("list={}",gson.toJson(cartVoResponseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),cartVoResponseVo.getStatus());
    }
   @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(uid);
        log.info("list={}",gson.toJson(list));

    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(10);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> list = cartService.update(uid,prdouctId,cartUpdateForm);
        log.info("list={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());

    }

    @Test
    public void delete(){
        log.info("删除购物车...");
        ResponseVo<CartVo> delete = cartService.delete(uid, prdouctId);
        log.info("delete={}",gson.toJson(delete));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),delete.getStatus());
    }

    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> unSelectAll = cartService.unSelectAll(uid);
        log.info("unSelectAll={}",gson.toJson(unSelectAll));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),unSelectAll.getStatus());
    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> unSelectAll = cartService.selectAll(uid);
        log.info("selectAll={}",gson.toJson(unSelectAll));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),unSelectAll.getStatus());
    }

    @Test
    public void sum(){
        ResponseVo<Integer> sum = cartService.sum(uid);
        log.info("selectAll={}",gson.toJson(sum));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),sum.getStatus());
    }
}