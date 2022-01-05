package com.soul.blog.service;

import com.soul.blog.vo.CategoryVo;
import com.soul.blog.vo.Result;

public interface CategoryService {

  CategoryVo findCategoryById(Long categoryId);

  Result findAll();

  Result findAllDetail();

  Result categoryDetailById(Long id);
}
