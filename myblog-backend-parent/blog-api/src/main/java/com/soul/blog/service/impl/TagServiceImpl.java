package com.soul.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.blog.dao.mapper.TagMapper;
import com.soul.blog.dao.pojo.Tag;
import com.soul.blog.service.TagService;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.TagVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TagMapper tagMapper;

  @Override
  public List<TagVo> findTagsByArticleId(Long articleId) {
    List<Tag> tagesByArticleId = tagMapper.findTagsByArticleId(articleId);
    return copyList(tagesByArticleId);
  }

  /**
   * get tags which maps most articles (hot tag)
   * @param limit
   * @return
   */
  @Override
  public Result findHotTags(int limit) {
    List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);

    if (CollectionUtils.isEmpty(hotsTagIds)) {
      return Result.success(Collections.emptyList());
    }

    List<Tag> hotTags = tagMapper.findTagsByTagIds(hotsTagIds);
    return Result.success(hotTags);
  }

  @Override
  public Result findAll() {
    LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.select(Tag::getId, Tag::getTagName);
    List<Tag> tags = this.tagMapper.selectList(queryWrapper);

    return Result.success(copyList(tags));
  }

  @Override
  public Result findAllDetail() {
    LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
    List<Tag> tags = this.tagMapper.selectList(queryWrapper);
    return Result.success(copyList(tags));
  }

  @Override
  public Result findDetailById(Long id) {
    Tag tag = tagMapper.selectById(id);
    return Result.success(copy(tag));
  }

  /**
   * convert List<Tag> to List<TagVo>
   * @param tagesByArticleId
   * @return
   */
  private List<TagVo> copyList(List<Tag> tagesByArticleId) {
    ArrayList<TagVo> tagVoList = new ArrayList<>();
    tagesByArticleId.forEach(
        tag -> tagVoList.add(this.copy(tag))
    );
    return tagVoList;
  }

  /**
   * convert a Tag to TagVo
   * @param tag
   * @return
   */
  private TagVo copy(Tag tag) {
    TagVo tagVo = new TagVo();
    BeanUtils.copyProperties(tag, tagVo);
    tagVo.setId(String.valueOf(tag.getId()));
    return tagVo;
  }
}
