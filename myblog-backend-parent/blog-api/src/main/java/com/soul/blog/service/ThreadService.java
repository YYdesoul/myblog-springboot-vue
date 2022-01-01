package com.soul.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.soul.blog.dao.mapper.ArticleMapper;
import com.soul.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

  // 期望此操作在线程池执行， 不会影响原有的主线程
  @Async("taskExecutor")
  public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

    Integer viewCounts = article.getViewCounts();
    Article articleUpdate = new Article();
    articleUpdate.setViewCounts(viewCounts + 1);
    LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
    // 设置一个 为了在多线程的环境下线程安全
    updateWrapper.eq(Article::getViewCounts, viewCounts);
    // update article set view_count=100 where view_count = 99 and id = 11
    articleMapper.update(articleUpdate, updateWrapper);

//    线程池测试
//    try {
//      Thread.sleep(5000);
//      System.out.println("更新完成了...");
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }
}
