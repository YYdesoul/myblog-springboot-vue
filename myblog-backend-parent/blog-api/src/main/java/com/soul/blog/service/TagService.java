package com.soul.blog.service;

import com.soul.blog.vo.Result;
import com.soul.blog.vo.TagVo;
import java.util.List;

public interface TagService {

  List<TagVo> findTagsByArticleId(Long articleId);

  Result findHotTags(int limit);

  /**
   * 查询所有的文章标签
   * @return
   */
  Result findAll();

  Result findAllDetail();

  Result findDetailById(Long id);
}
