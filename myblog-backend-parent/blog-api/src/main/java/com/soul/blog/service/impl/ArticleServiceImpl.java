package com.soul.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soul.blog.dao.dos.Archives;
import com.soul.blog.dao.mapper.ArticleBodyMapper;
import com.soul.blog.dao.mapper.ArticleMapper;
import com.soul.blog.dao.mapper.ArticleTagMapper;
import com.soul.blog.dao.pojo.ArticleBody;
import com.soul.blog.dao.pojo.ArticleTag;
import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.service.ArticleService;
import com.soul.blog.service.CategoryService;
import com.soul.blog.service.SysUserService;
import com.soul.blog.service.TagService;
import com.soul.blog.service.ThreadService;
import com.soul.blog.utils.UserThreadLocal;
import com.soul.blog.vo.ArticleBodyVo;
import com.soul.blog.vo.ArticleVo;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.TagVo;
import com.soul.blog.vo.params.ArticleParam;
import com.soul.blog.vo.params.PageParams;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soul.blog.dao.pojo.Article;

@Service
@Log4j2
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private TagService tagService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ThreadService threadService;

  @Autowired
  private ArticleBodyMapper articleBodyMapper;

  @Autowired
  private ArticleTagMapper articleTagMapper;



  @Override
  public Result listArticle(PageParams pageParams) {
    Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

    IPage<Article> articleIPage = articleMapper.listArticle(
        page,
        pageParams.getCategoryId(),
        pageParams.getTagId(),
        pageParams.getYear(),
        pageParams.getMonth());
    List<Article> records = articleIPage.getRecords();
    return Result.success(copyList(records,true,true));
  }

  @Override
  public Result findHotArticles(int limit) {
    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(Article::getViewCounts);
    queryWrapper.select(Article::getId, Article::getTitle);
    queryWrapper.last("limit " + limit);
    // select id, titile from article order by view_counts desc limit 5
    List<Article> hotArticles = articleMapper.selectList(queryWrapper);

    return Result.success(copyList(hotArticles, false, false));
  }

  @Override
  public Result findNewestArticles(int limit) {
    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(Article::getCreateDate);
    queryWrapper.select(Article::getId, Article::getTitle);
    queryWrapper.last("limit " + limit);
    // select id, titile from article order by create_date desc limit 5
    List<Article> hotArticles = articleMapper.selectList(queryWrapper);

    return Result.success(copyList(hotArticles, false, false));
  }

  @Override
  public Result listArchives() {
    List<Archives> archives = articleMapper.listArchives();
    return Result.success(archives);
  }

  /**
   * find content of article
   * @param articleId
   * @return
   */
  @Override
  public Result findArticleById(Long articleId) {
    /**
     * 1. 根据id查询 文章信息
     * 2. 根据bodyId和categoryid 去做关联查询
     */
    Article article = articleMapper.selectById(articleId);
    ArticleVo articleVo = copy(article, true, true, true, true);
    threadService.updateArticleViewCount(articleMapper, article);

    return Result.success(articleVo);
  }

  @Override
  public Result publish(ArticleParam articleParam) {
    // 此接口 要加入到登录拦截当中
    SysUser sysUser = UserThreadLocal.get();

    /**
     * 1. 发布文章 目的 构建Article对象
     * 2. 作者id 当前登录的用户
     * 3. 标签 要将标签加入到关联列表当中
     * 4. body 内容存储
     */
    Article article = Article.builder()
        .authorId(sysUser.getId())
        .weight(Article.Article_Common)
        .viewCounts(0)
        .title(articleParam.getTitle())
        .summary(articleParam.getSummary())
        .commentCounts(0)
        .createDate(System.currentTimeMillis())
        .categoryId(articleParam.getCategory().getId())
        .build();
    this.articleMapper.insert(article);
    // tag
    List<TagVo> tagVos = articleParam.getTags();
    if (tagVos != null) {
      for (TagVo tagVo : tagVos) {
        Long articleId = article.getId();
        ArticleTag articleTag = ArticleTag.builder()
            .tagId(tagVo.getId())
            .articleId(articleId)
            .build();
      articleTagMapper.insert(articleTag);
      }
    }
    // article body
    ArticleBody articleBody = ArticleBody.builder()
        .articleId(article.getId())
        .content(articleParam.getBody().getContent())
        .contentHtml(articleParam.getBody().getContentHtml())
        .build();
    articleBodyMapper.insert(articleBody);
    article.setBodyId(articleBody.getId());
    articleMapper.updateById(article);
    ArticleVo articleVo = ArticleVo.builder().id(article.getId()).build();
    return Result.success(articleVo);
  }

  /**
   * generate a ArticleVoList and copy all values in ArticleList into it
   *
   * @param records
   * @return
   */
  private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
    ArrayList<ArticleVo> articleVoList = new ArrayList<>();
    records.forEach(
        (record) -> articleVoList.add(this.copy(record, isTag, isAuthor, false, false))
    );
    return articleVoList;
  }

  private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,
      boolean isBody, boolean isCategory) {
    ArrayList<ArticleVo> articleVoList = new ArrayList<>();
    records.forEach(
        (record) -> articleVoList.add(this.copy(record, isTag, isAuthor, isBody, isCategory))
    );
    return articleVoList;
  }

  /**
   * generate a ArticleVo and copy all values in article into it
   *
   * @param article
   * @return
   */
  private ArticleVo copy(Article article, boolean isTag, boolean isAuthor,
      boolean isBody, boolean isCategory) {
    ArticleVo articleVo = new ArticleVo();
    BeanUtils.copyProperties(article, articleVo);

    if (isTag) {
      List<TagVo> tagesByArticleId = tagService.findTagsByArticleId(article.getId());
      articleVo.setTags(tagesByArticleId);
    }

    if (isAuthor) {
      SysUser sysUser = sysUserService.findUserById(article.getAuthorId());
      articleVo.setAuthor(sysUser.getNickname());
    }

    if (isBody) {
      Long bodyId = article.getBodyId();
      articleVo.setBody(findArticleBodyById(bodyId));
    }

    if (isCategory) {
      Long categoryId = article.getCategoryId();
      articleVo.setCategory(categoryService.findCategoryById(categoryId));
    }

    articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    return articleVo;
  }

  private ArticleBodyVo findArticleBodyById(Long bodyId) {
    ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
    ArticleBodyVo articleBodyVo = ArticleBodyVo.builder()
        .content(articleBody.getContent())
        .build();
    return articleBodyVo;
  }
}
