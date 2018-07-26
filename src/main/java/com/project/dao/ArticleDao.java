package com.project.dao;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface ArticleDao {
    /**
     * 查询所有文章，分页
     * @return
     */
    List<Map<String,Object>> getArticles();

    /**
     * 查询单个文章
     * @param articleId
     * @return
     */
    Map<String,Object> getArticleById(int articleId);

    /**
     * 新增文章
     * @param article
     * @return
     */
    int createArticle(Map<String,Object> article);

    /**
     * 修改文章信息
     * @param article
     * @return
     */
    int updateArticle(Map<String,Object> article);

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    int deleteArticle(int articleId);

    /**
     * 获取type_ids
     * @return
     */
    List<Map<String,Object>> getTypeIds();

    /**
     * 查询文章和类型的关联关系
     * @param articleId
     * @return
     */
    List<Map<String,Object>> getTypeByArticle(int articleId);

    /**
     * 创建文章和类型的关联关系
     * @param parameter
     * @return
     */
    int createRelationTypeWithArticle(Map<String,Object> parameter);

    /**
     *  查询出创建文章和类型的关联关系的最新id
     * @return
     */
    int findMaxTypeId();

    /**
     * 删除文章和类型的关联关系
     * @param articleId
     * @return
     */
    int deleteRelationTypeWithArticle(int articleId);

    /**
     * 查询文章和关联试题
     * @param articleId
     * @return
     */
    List<Map<String,Object>> getQuestionByArticle(int articleId);

    /**
     * 创建文章和试题的关联关系
     * @param parameter
     * @return
     */
    int createRelationQuestionWithArticle(Map<String,Object> parameter);

    /**
     *  查询出创建文章和试题的关联关系的最新id
     * @return
     */
    int findMaxAqId();

}
