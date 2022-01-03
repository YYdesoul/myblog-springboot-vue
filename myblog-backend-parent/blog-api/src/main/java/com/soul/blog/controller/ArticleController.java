package com.soul.blog.controller;

import com.soul.blog.service.ArticleService;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.ArticleParam;
import com.soul.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for getting article
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  /**
   * return articles to the index page.
   * @param pageParams
   * @return
   */
  @PostMapping
  //加上此注解 代表要对此接口记录日志
  public Result listArticle(@RequestBody PageParams pageParams) {
    return articleService.listArticle(pageParams);
  }

  /**
   * find most viewed article and show them in the index page
   * @return
   */
  @PostMapping("hot")
  public Result findHotArticles() {
    int limit = 5;
    return articleService.findHotArticles(limit);
  }

  /**
   * find newest article and show them in the index page
   * @return
   */
  @PostMapping("new")
  public Result findNewestArticles() {
    int limit = 5;
    return articleService.findNewestArticles(limit);
  }

  /**
   * 文章归档 显示在首页
   * @return
   */
  @PostMapping("listArchives")
  public Result listArchives() {
    return articleService.listArchives();
  }

  @PostMapping("view/{id}")
  public Result findArticleById(@PathVariable("id") Long articleId) {
    return articleService.findArticleById(articleId);
  }

  @PostMapping("publish")
  public Result publish(@RequestBody ArticleParam articleParam) {
    return articleService.publish(articleParam);
  }
}
