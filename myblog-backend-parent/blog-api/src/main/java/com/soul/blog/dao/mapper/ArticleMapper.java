package com.soul.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soul.blog.dao.dos.Archives;
import com.soul.blog.dao.pojo.Article;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {

  List<Archives> listArchives();

  IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
