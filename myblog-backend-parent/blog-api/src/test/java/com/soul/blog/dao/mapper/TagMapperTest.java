package com.soul.blog.dao.mapper;

import com.soul.blog.dao.pojo.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

@SpringBootTest
public class TagMapperTest {

  @Autowired
  private TagMapper tagMapper;

  @Test
  public void findTagsByArticleIdTest() {
//    System.out.println("hello");
    List<Tag> tagsByArticleId = tagMapper.findTagsByArticleId(1L);
    System.out.println(tagsByArticleId);
    if (!CollectionUtils.isEmpty(tagsByArticleId)) {
      tagsByArticleId.forEach(System.out::println);
    }
  }

  @Test
  public void findHotsTagIdsTest() {
    List<Long> hotsTagIds = tagMapper.findHotsTagIds(5);
    if (!CollectionUtils.isEmpty(hotsTagIds)) {
      System.out.println(hotsTagIds.toString());
    }
  }

  @Test
  public void findTagsByTagIdsTest() {
    List<Long> hotsTagIds = tagMapper.findHotsTagIds(5);

    List<Tag> hotTags = tagMapper.findTagsByTagIds(hotsTagIds);
    if (!CollectionUtils.isEmpty(hotTags)) {
      System.out.println(hotTags.toString());
    }
  }

  @Test
  public void testArrays() {

  }


}
