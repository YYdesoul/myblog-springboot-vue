package com.soul.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.blog.dao.mapper.CategoryMapper;
import com.soul.blog.dao.pojo.Category;
import com.soul.blog.service.CategoryService;
import com.soul.blog.vo.CategoryVo;
import com.soul.blog.vo.Result;
import java.util.ArrayList;
import java.util.List;
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

  @Override
  public Result findAll() {
    List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
    // 页面交互的对象
    return Result.success(copyList(categories));
  }

  private List<CategoryVo> copyList(List<Category> categories) {
    ArrayList<CategoryVo> categoryVos = new ArrayList<>();
    categories.forEach(
        category -> categoryVos.add(this.copy(category))
    );
    return categoryVos;
  }

  private CategoryVo copy(Category category) {
    CategoryVo categoryVo = new CategoryVo();
    BeanUtils.copyProperties(category, categoryVo);
    return categoryVo;
  }
}
