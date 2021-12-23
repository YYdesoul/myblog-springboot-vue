package com.soul.blog.service;

import com.soul.blog.vo.CategoryVo;

public interface CategoryService {

  CategoryVo findCategoryById(Long categoryId);
}
