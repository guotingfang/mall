package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.ResponseVo;

/**
 * Greated by Guo
 *
 * @date2020/5/9 23:22
 */

public interface IProductService {

   ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);
}
