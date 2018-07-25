package com.project.service.impl;

import com.project.dao.ArticleDao;
import com.project.service.ArticleService;
import com.project.util.DateUtil;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import com.project.util.ResultUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        Map<String,Object> res = new HashMap<>();
        try {
            List<Map<String,Object>> list = articleDao.getArticles();
            if(list.size() > 0){
                List<Map<String,Object>> typeIds = articleDao.getTypeIds();
                Map<String,List<Map<String,Object>>> tmp = parseTypeIds(typeIds);
                for(Map<String,Object> map:list){
                    String article = map.get("article_id").toString();
                    if(tmp.keySet().contains(article)){
                        map.put("type_ids",tmp.get(article));
                    }else{
                        map.put("type_ids",new ArrayList<>());
                    }
                }
            }
            res.put("total", list.size());
            res.put("rows",list);
            return res;
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
            if(article != null){
                List<Map<String,Object>> typeIds = articleDao.getTypeIds();
                Map<String,List<Map<String,Object>>> tmp = parseTypeIds(typeIds);
                String key = article.get("article_id").toString();
                if(tmp.keySet().contains(key)){
                    article.put("type_ids",tmp.get(key));
                }else{
                    article.put("type_ids",new ArrayList<>());
                }
            }
            return article;
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
        if(!article.keySet().contains("recommend")){
            article.put("recommend",0);
        }
        if(!article.keySet().contains("status")){
            article.put("status",1);
        }
        if(!article.keySet().contains("period")){
            article.put("period",0);
        }
        if(!article.keySet().contains("coursetype")){
            article.put("coursetype",1);
        }
        if(!article.keySet().contains("banner")){
            article.put("banner",1);
        }
        if(!article.keySet().contains("dept_id")){
            article.put("dept_id",0);
        }
        if(!article.keySet().contains("article_fid")){
            article.put("article_fid",0);
        }
        if(!article.keySet().contains("user_id")){
            article.put("user_id","undefined");
        }
        article.put("created",DateUtil.getTodayTimeStamp());
        article.put("updated",DateUtil.getTodayTimeStamp());
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

    /**
     * 格式化typeids
     * @return
     */
    private Map<String,List<Map<String,Object>>> parseTypeIds(List<Map<String,Object>> list){
        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        for(Map<String,Object> obj : list){
            String article = obj.get("article_id").toString();
            if(map.keySet().contains(article)){
                map.get(article).add(obj);
            }else{
                List<Map<String,Object>> tmp = new ArrayList<>();
                tmp.add(obj);
                map.put(article,tmp);
            }
        }
        return map;
    }

    /**
     * 查询文章和类型的关联关系
     * @param articleId
     * @return
     */
    public Object getTypeByArticle(int articleId) {
        List<Map<String,Object>> res = null;
        try {
            res = articleDao.getTypeByArticle(articleId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 创建文章和类型的关联关系
     * @param articleId
     * @return
     */
    public Object createRelationTypeWithArticle(HttpServletRequest req, int articleId) {
        try{
            JsonUtil obj = new JsonUtil(requestUtil.getBody(req));
            int type_id = obj.getIntOrElse("type_id",-1);
            if(type_id != -1){
                Map<String,Object> parameter = new HashMap<>();
                parameter.put("type_id",type_id);
                parameter.put("article_id",articleId);
                int flag = articleDao.createRelationTypeWithArticle(parameter);
                if(flag == 1){
                    return ResultUtil.success("成功");
                }else {
                    return ResultUtil.error(500,"创建关系失败");
                }
            }else{
                return ResultUtil.error(500,"type_id参数异常");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(500,"内部异常");
        }
    }

    /**
     * 删除文章和类型的关联关系
     * @param articleId
     * @return
     */
    public Object deleteRelationTypeWithArticle(int articleId) {
        try{
            int flag = articleDao.deleteRelationTypeWithArticle(articleId);
            if(flag > 0){
                return ResultUtil.success("删除成功");
            }else {
                return ResultUtil.success("关联关系已经删除完");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(500,"内部异常");
        }
    }
}
