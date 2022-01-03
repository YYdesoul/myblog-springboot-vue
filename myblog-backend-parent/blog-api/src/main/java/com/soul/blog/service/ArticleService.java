package com.soul.blog.service;

import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.ArticleParam;
import com.soul.blog.vo.params.PageParams;

public interface ArticleService {

  /**
   * searching article list with paging
   * @param pageParams
   * @return
   */
  Result listArticle(PageParams pageParams);

  Result findHotArticles(int limit);

  Result findNewestArticles(int limit);

  Result listArchives();

  Result findArticleById(Long articleId);

  /**
   * 文章发布服务
   * @param articleParam
   * @return
   */
  Result publish(ArticleParam articleParam);
}
