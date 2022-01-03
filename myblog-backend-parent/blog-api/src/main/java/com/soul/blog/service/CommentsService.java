package com.soul.blog.service;

import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.CommentParam;

public interface CommentsService {

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     */
    public Result commentsByArticleId(Long id);

  Result comment(CommentParam commentParam);
}
