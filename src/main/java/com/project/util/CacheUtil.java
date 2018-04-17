package com.project.util;

import com.project.model.User;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/16.
 */
public class CacheUtil {

    private static EhCacheCacheManager cacheCacheManager;
    private static CacheManager cacheManager;
    private static Cache cache;
    private static CacheUtil cacheUtil = null;

    private CacheUtil() {
        //获取EhCacheCacheManager类
        cacheCacheManager= ApplicationContextUtils.getApplicationContext().getBean(EhCacheCacheManager.class);
        //获取CacheManager类
        cacheManager=cacheCacheManager.getCacheManager();
        //获取Cache
        cache = cacheManager.getCache("tokenCache");
    }

    /**
     * 单列模式通过一个方法获取
     * @return
     */
    public static CacheUtil getInstance(){
        if(cacheUtil == null){
            cacheUtil = new CacheUtil();
        }
        return cacheUtil;
    }

    /**
     * 使用ehcache来做token缓存
     * @param key
     * @param obj
     */
    public void put(String key,Object obj){
        cache.put(new Element(key, obj));
    }

    /**
     * 获取缓存中的值
     * @param key
     * @return
     */
    public Object get(String key){
        Object obj = null;
        try{
            Element element = cache.get(key);
            if(element != null){
                obj = element.getObjectValue();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(obj != null){
            return obj;
        }else{
            return null;
        }
    }

    /**
     * 删除key
     * @param key
     */
    public boolean remove(String key){
        boolean flag = false;
        try {
            flag = cache.remove(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取缓存中的对象
     * @param req
     * @return
     */
    public User getUser(HttpServletRequest req){
        User user = null;
        try{
            String token = req.getHeader("token");
            user = (User)CacheUtil.getInstance().get(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
