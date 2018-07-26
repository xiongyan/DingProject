package com.project.controller;

import com.project.service.ArticleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 查询所有文章，分页
     * @return
     */
    @RequestMapping(value="/article",method= RequestMethod.GET)
    public Object getArticles() {
        return articleService.getArticles();
    }

    /**
     * 查询单个文章
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}",method= RequestMethod.GET)
    public Object getArticleById(@PathVariable int articleId){
        return articleService.getArticleById(articleId);
    }

    /**
     * 新增文章
     * @param req
     * @return
     */
    @RequestMapping(value="/article",method= RequestMethod.POST)
    public Object createArticle(HttpServletRequest req){
        return articleService.createArticle(req);
    }

    /**
     * 修改文章信息
     * @param req
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}",method= RequestMethod.POST)
    public Object updateArticle(HttpServletRequest req,@PathVariable int articleId){
        return articleService.updateArticle(req, articleId);
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}",method= RequestMethod.DELETE)
    public Object deleteArticle(@PathVariable int articleId){
        return articleService.deleteArticle(articleId);
    }

    /**
     * 查询文章和类型的关联关系
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/type",method= RequestMethod.GET)
    public Object getTypeByArticle(@PathVariable int articleId) {
        return articleService.getTypeByArticle(articleId);
    }

    /**
     * 创建文章和类型的关联关系
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/type",method= RequestMethod.POST)
    public Object createRelationTypeWithArticle(HttpServletRequest req,@PathVariable int articleId) {
        return articleService.createRelationTypeWithArticle(req,articleId);
    }

    /**
     * 删除文章和类型的关联关系
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/type",method= RequestMethod.DELETE)
    public Object deleteRelationTypeWithArticle(@PathVariable int articleId) {
        return articleService.deleteRelationTypeWithArticle(articleId);
    }

    /**
     * 查询文章和关联试题
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/question",method= RequestMethod.GET)
    public Object getQuestionByArticle(@PathVariable int articleId) {
        return articleService.getQuestionByArticle(articleId);
    }

    /**
     * 创建文章和试题关系
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/question",method= RequestMethod.POST)
    public Object createRelationQuestionWithArticle(HttpServletRequest req,@PathVariable int articleId) {
        return articleService.createRelationQuestionWithArticle(req, articleId);
    }

    /**
     * 删除文章和试题的关联关系
     * @param articleId
     * @return
     */
    @RequestMapping(value="/article/{articleId}/question",method= RequestMethod.DELETE)
    public Object deleteRelationQuestionWithArticle(@PathVariable int articleId) {
        return articleService.deleteRelationQuestionWithArticle(articleId);
    }
}
