package com.soul.blog.service.impl;

import com.soul.blog.dao.mapper.CategoryMapper;
import com.soul.blog.dao.pojo.Category;
import com.soul.blog.service.CategoryService;
import com.soul.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public CategoryVo findCategoryById(Long categoryId) {
    Category category = categoryMapper.selectById(categoryId);
    CategoryVo categoryVo = new CategoryVo();
    BeanUtils.copyProperties(category, categoryVo);
    return categoryVo;
  }
}