package com.imooc.mall.service.inpl;

import com.imooc.mall.dao.CategoryMapper;
import com.imooc.mall.pojo.Category;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.imooc.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * Greated by Guo
 *
 * @date2020/5/8 20:09
 */
@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 查找类目
     *
     * @return
     */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categoryList = categoryMapper.selectAll();
        List<CategoryVo> categoryVoList = new ArrayList<>();
        //获取一级目录
        for (Category category : categoryList){
            if (category.getParentId().equals(ROOT_PARENT_ID)){
                CategoryVo categoryVo = category2CategoryVo(category);

                categoryVoList.add(categoryVo);
                categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            }
        }
        //获取二级目录
        subCategoryVo(categoryVoList,categoryList);

        return ResponseVo.success(categoryVoList);
    }

    public void subCategoryVo( List<CategoryVo> categoryVoList, List<Category> categoryList){
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categoryList) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subcategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subcategoryVo);
                }
            }
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryVoList);
            subCategoryVo(subCategoryVoList, categoryList);
        }
    }


    public CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
