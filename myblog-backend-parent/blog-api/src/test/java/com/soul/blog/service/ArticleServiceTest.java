package com.soul.blog.service;

import com.soul.blog.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleServiceTest {

  @Autowired
  private ArticleService articleService;

  @Test
  public void fndArticleByIdTest() {
    Result articleResult = articleService.findArticleById(1L);
    if (articleResult != null) {
      System.out.println(articleResult.toString());
    } else {
      System.out.println("articleResult is null");
    }
  }
}
