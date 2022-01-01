package com.soul.blog.service;

import com.soul.blog.vo.Result;

public interface CommentsService {

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     */
    public Result commentsByArticleId(Long id);
}
