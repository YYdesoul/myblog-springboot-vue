package com.soul.blog.service.impl;

import com.soul.blog.service.CommentsService;
import com.soul.blog.vo.Result;

public class CommentsServiceImpl implements CommentsService {
    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1. 根据文章id 查询评论列表，从comment表查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果level = 1 要去查询它有么有子id
         *
         */
        return null;
    }
}
