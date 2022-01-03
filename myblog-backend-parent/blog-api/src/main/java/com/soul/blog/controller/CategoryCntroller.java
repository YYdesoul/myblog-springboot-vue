package com.soul.blog.controller;


import com.soul.blog.service.CategoryService;
import com.soul.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryCntroller {

  @Autowired
  private CategoryService categoryService;

  // /categorys
  @GetMapping
  public Result categories() {
    return categoryService.findAll();
  }
}
