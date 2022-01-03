package com.soul.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.blog.dao.mapper.CommentsMapper;
import com.soul.blog.dao.pojo.Comment;
import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.service.CommentsService;
import com.soul.blog.service.SysUserService;
import com.soul.blog.utils.UserThreadLocal;
import com.soul.blog.vo.CommentVo;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.UserVo;
import com.soul.blog.vo.params.CommentParam;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1. 根据文章id 查询评论列表，从comment表查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果level = 1 要去查询它有么有子id
         *
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> commentList = commentsMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(commentList);

        return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        log.info(commentParam.toString());
        SysUser sysUser = UserThreadLocal.get();
        Comment comment =
            Comment.builder()
            .articleId(commentParam.getArticleId())
            .authorId(sysUser.getId())
            .content(commentParam.getContent())
            .createDate(System.currentTimeMillis())
            .build();
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
          comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        // 这里有问题
        if (toUserId == null) {
            toUserId = -1L;
        }
        comment.setToUid(toUserId);
        this.commentsMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        ArrayList<CommentVo> commentVoList = new ArrayList<>();
        commentList.forEach(
            comment -> commentVoList.add(copy(comment))
        );
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        // 作者信息
        Long authorId = comment.getAuthorId();
        UserVo author = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(author);
        // 子评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long commentId = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(commentId);
            commentVo.setChildrens(commentVoList);
        }
        // to User 给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);

        return copyList(commentsMapper.selectList(queryWrapper));
    }
}
