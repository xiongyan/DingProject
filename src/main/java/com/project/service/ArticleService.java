package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface ArticleService {
    /**
     * 查询所有文章，分页
     * @return
     */
    Object getArticles();

    /**
     * 查询单个文章
     * @param articleId
     * @return
     */
    Object getArticleById(int articleId);

    /**
     * 新增文章
     * @param req
     * @return
     */
    Object createArticle(HttpServletRequest req);

    /**
     * 修改文章信息
     * @param userId
     * @param req
     * @return
     */
    Object updateArticle(HttpServletRequest req, int userId);


    /**
     * 删除文章
     * @param userId
     * @return
     */
    Object deleteArticle(int userId);

    /**
     * 查询文章和类型的关联关系
     * @param articleId
     * @return
     */
    Object getTypeByArticle(int articleId);

    /**
     * 创建文章和类型的关联关系
     * @param articleId,req
     * @return
     */
    Object createRelationTypeWithArticle(HttpServletRequest req,int articleId);

    /**
     * 删除文章和类型的关联关系
     * @param articleId
     * @return
     */
    Object deleteRelationTypeWithArticle(int articleId);

    /**
     * 查询文章和关联试题
     * @param articleId
     * @return
     */
    Object getQuestionByArticle(int articleId);

    /**
     * 创建文章和试题关系
     * @param articleId
     * @return
     */
    Object createRelationQuestionWithArticle(HttpServletRequest req,int articleId);
}

