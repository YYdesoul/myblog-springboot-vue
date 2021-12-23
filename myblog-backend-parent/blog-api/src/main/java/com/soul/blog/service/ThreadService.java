package com.soul.blog.service;

import com.soul.blog.dao.mapper.ArticleMapper;
import com.soul.blog.dao.pojo.Article;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

  // 期望此操作在线程池执行， 不会影响原有的主线程
  public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
    try {
      Thread.sleep(5000);
      System.out.println("更新完成了...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
