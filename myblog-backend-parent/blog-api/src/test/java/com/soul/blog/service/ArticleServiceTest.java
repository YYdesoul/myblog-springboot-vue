//package com.soul.blog.service;
//
//import com.soul.blog.vo.Result;
//import com.soul.blog.vo.params.PageParams;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ArticleServiceTest {
//
//  @Autowired
//  private ArticleService articleService;
//
//  @Test
//  void testListArticle() {  //TODO: has problem
//    PageParams pageParams = PageParams.builder().build();
//    System.out.println(pageParams.toString());
//    System.out.println(articleService.listArticle(pageParams));
//  }
//
//
//  @Test
//  public void findArticleByIdTest() {
//    Result articleResult = articleService.findArticleById(1L);
//    if (articleResult != null) {
//      System.out.println(articleResult.toString());
//    } else {
//      System.out.println("articleResult is null");
//    }
//  }
//
//
//}
