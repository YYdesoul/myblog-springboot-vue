package com.soul.blog.vo.params;

import com.soul.blog.vo.CategoryVo;
import com.soul.blog.vo.TagVo;
import java.util.List;
import lombok.Data;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
