package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Greated by Guo
 *
 * @date2020/5/10 23:36
 */

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;
    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        return productService.list(categoryId,pageNum,pageSize);
    }
}