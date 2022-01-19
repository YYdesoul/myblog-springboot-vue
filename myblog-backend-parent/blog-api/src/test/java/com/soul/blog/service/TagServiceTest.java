//package com.soul.blog.service;
//
//import com.soul.blog.vo.Result;
//import com.soul.blog.vo.TagVo;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class TagServiceTest {
//
//  @Autowired
//  private TagService tagService;
//
//  @Test
//  public void testFindTagsByArticleId() {
//    List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(1L);
//    System.out.println(tagsByArticleId.toString());
//  }
//
//  @Test
//  public void findHotTagsTest() {
//    Result hotTags = tagService.findHotTags(6);
//    System.out.println(hotTags.toString());
//  }
//}
