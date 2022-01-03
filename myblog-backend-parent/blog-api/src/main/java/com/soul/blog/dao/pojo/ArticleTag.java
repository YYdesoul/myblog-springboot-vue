package com.soul.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
