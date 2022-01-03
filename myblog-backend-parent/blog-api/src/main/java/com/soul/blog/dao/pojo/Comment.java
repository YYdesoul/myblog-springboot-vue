package com.soul.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

  private Long id;

  private String content;

  private Long createDate;

  private Long articleId;

  private Long authorId;

  private Long parentId;

  private Long toUid;

  private Integer level;
}

