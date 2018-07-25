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

}
