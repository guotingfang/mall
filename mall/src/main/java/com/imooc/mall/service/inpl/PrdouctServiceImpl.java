package com.imooc.mall.service.inpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.PrdouctVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Greated by Guo
 *
 * @date2020/5/9 23:22
 */
@Service
@Slf4j
public class PrdouctServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null){
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<PrdouctVo> prdouctVoList = products.stream()
                .map(e -> {
                    PrdouctVo prdouctVo = new PrdouctVo();
                    BeanUtils.copyProperties(e, prdouctVo);
                    return prdouctVo;
                })
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo<>(products);
        pageInfo.setList(prdouctVoList);
        return ResponseVo.success(pageInfo);
    }
}
