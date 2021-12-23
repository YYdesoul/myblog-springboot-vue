package com.soul.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pojo for the content of article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleBody {

  private Long id;

  private String content;

  private String contentHtml;

  private Long articleId;
}
