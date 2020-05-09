package com.imooc.mall.utils;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.pojo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Greated by Guo
 *
 * @date2020/5/8 23:56
 */

public class TreeCategoryUtil {

    public static List<CategoryVo> transCategoryPOS(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<CategoryVo>();
        for (Category category : categoryList) {
            categoryVoList.add(transCategoryPO(category));
        }
            return categoryVoList;
        }

    public static CategoryVo transCategoryPO(Category category){
        CategoryVo categoRyVo = new CategoryVo();
        if (category.getId() >= 0){
            categoRyVo.setId(category.getId());
            categoRyVo.setParentId(category.getParentId());
            categoRyVo.setName(category.getName());
            categoRyVo.setSortOrder(category.getSortOrder());
        }
        return categoRyVo;
    }
}
