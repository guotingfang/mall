package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * Greated by Guo
 *
 * @date2020/5/8 20:06
 */

public interface ICategoryService {

    /**
     * 查找类目
     * @return
     */
    ResponseVo<List<CategoryVo>> selectAll();

}
