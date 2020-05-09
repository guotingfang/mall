package com.imooc.mall.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Greated by Guo
 *
 * @date2020/5/8 23:02
 */

@Data
public class CategoryVo {

    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;

    private List<CategoryVo> subCategories = new ArrayList<CategoryVo>();

}
