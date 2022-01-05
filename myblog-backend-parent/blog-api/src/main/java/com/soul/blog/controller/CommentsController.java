package com.soul.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soul.blog.service.CommentsService;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.CommentParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
@Log4j2
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    // /comments/article/{id}
    @GetMapping("article/{id}")
    public Result comments (@PathVariable("id") Long id) {
        return commentsService.commentsByArticleId(id);
    }

    /**
     * 创建评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        log.info("enter comment method in CommentsController");
        log.info("commentParam is: " + commentParam.toString());
//        return Result.success(null);
        return commentsService.comment(commentParam);
    }
}
