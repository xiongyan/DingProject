package com.project.service.impl;

import com.project.dao.ArticleDao;
import com.project.model.User;
import com.project.service.ArticleService;
import com.project.util.DateUtil;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import com.project.util.ResultUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Resource
    private RequestUtil requestUtil;

    /**
     * 查询所有文章，分页
     * @return
     */
    @Override
    public Object getArticles() {
        try {
            List<Map<String,Object>> list = articleDao.getArticles();
            return ResultUtil.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(500,"内部查询错误");
        }
    }


    /**
     * 查询单个文章
     * @param articleId
     * @return
     */
    public Object getArticleById(int articleId){
        try {
            Map<String,Object> article = articleDao.getArticleById(articleId);
            return ResultUtil.success(article);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(500,"内部查询错误");
        }
    }

    /**
     * 新增文章
     * @param req
     * @return
     */
    @Override
    public Object createArticle(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        Map<String,Object> article = new HashMap<>();
        for(String key : jsonObject.keySet()){
            article.put(key,jsonObject.get(key));
        }
        article.put("created",DateUtil.getTodayTimeStamp());
        //保存新article
        int flag = articleDao.createArticle(article);
        if(flag == 1){
            return ResultUtil.success("新增文章成功");
        }else{
            return ResultUtil.error(500,"由于内部错误，新增文章失败");
        }
    }

    /**
     * 修改文章信息
     * @param article_id
     * @param req
     * @return
     */
    public Object updateArticle(HttpServletRequest req, int article_id){
        JSONObject jsonObject = requestUtil.getBody(req);
        Map<String,Object> article = new HashMap<>();
        for(String key : jsonObject.keySet()){
            article.put(key,jsonObject.get(key));
        }
        article.put("article_id",article_id);
        article.put("updated",DateUtil.getTodayTimeStamp());
        int flag = articleDao.updateArticle(article);
        if(flag == 1){
            return ResultUtil.success("修改文章内容成功");
        }else {
            return ResultUtil.error(500,"由于内部错误，修改文章内容失败");
        }
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    public Object deleteArticle(int articleId){
       try {
           int res = articleDao.deleteArticle(articleId);
           if(res == 1){
               return ResultUtil.success("成功删除文章");
           }else{
               return ResultUtil.error(200,"删除文章失败");
           }
       }catch (Exception e){
           e.printStackTrace();
           return ResultUtil.error(500,"内部异常");
       }
    }

}
