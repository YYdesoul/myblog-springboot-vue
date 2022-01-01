package com.soul.blog.controller;

import com.soul.blog.service.CommentsService;
import com.soul.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    // /comments/article/{id}
    @PostMapping("article/{id}")
    public Result comments (@PathVariable("id") Long id) {
//            return null;
        return commentsService.commentsByArticleId(id);
    }
}
