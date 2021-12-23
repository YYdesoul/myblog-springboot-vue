package com.soul.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

  public static final int Article_TOP = 1;

  public static final int Article_Common = 0;

  private Long id;

  private String title;

  private String summary;

  private Integer commentCounts;

  private Integer viewCounts;

  private Long authorId;

  private Long bodyId; // content id

  private Long categoryId;

  private Integer weight; // 置顶

  private Long createDate;
}
