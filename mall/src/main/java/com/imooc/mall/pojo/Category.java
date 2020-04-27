package com.imooc.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 *
 * po(persistent object)
 * pojo(plian ordinary java object)
 * Created by 廖师兄
 * 2035-01-18 18:13
 */
@Data
public class Category {

	private Integer id;

	private Integer parentId;

	private String name;

	private Integer status;

	private Integer sortOrder;

	private Date createTime;

	private Date updateTime;
}
