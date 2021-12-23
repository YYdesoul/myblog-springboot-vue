package com.soul.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soul.blog.dao.pojo.Tag;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapper extends BaseMapper<Tag> {


  List<Tag> findTagsByArticleId(Long articleId);

  List<Long> findHotsTagIds(int limit);

  List<Tag> findTagsByTagIds(List<Long> tagIds);

}
