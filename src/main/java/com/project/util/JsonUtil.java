package com.project.util;

import org.json.JSONObject;

/**
 * Created by laishun on 2018/4/3.
 */
public class JsonUtil {
    private JSONObject json;

    public JsonUtil(JSONObject json){
        this.json = json;
    }

    /**
     * 获取String值如果不存在返回空
     * @param key
     * @return
     */
    public String getStringOrElse(String key){
        if(json.has(key)){
            return json.getString(key);
        }else {
            return "";
        }
    }

    /**
     * 获取String值如果不存在返回默认值
     * @param key
     * @param defaultVal
     * @return
     */
    public String getStringOrElse(String key,String defaultVal){
        if(json.has(key)){
            return json.getString(key);
        }else {
            return defaultVal;
        }
    }

    /**
     * 获取Int值如果不存在返回默认值
     * @param key
     * @param defaultVal
     * @return
     */
    public int getIntOrElse(String key,int defaultVal){
        if(json.has(key)){
            return json.getInt(key);
        }else {
            return defaultVal;
        }
    }

    /**
     * 获取boolean值如果不存在返回默认值
     * @param key
     * @param defaultVal
     * @return
     */
    public boolean getBooleanOrElse(String key,boolean defaultVal){
        if(json.has(key)){
            return json.getBoolean(key);
        }else {
            return defaultVal;
        }
    }

    /**
     * 获取Int值如果不存在返回默认值
     * @param key
     * @param defaultVal
     * @return
     */
    public double getDoubbleOrElse(String key,double defaultVal){
        if(json.has(key)){
            return json.getDouble(key);
        }else {
            return defaultVal;
        }
    }

    /**
     * 获取对象如果不存在返回默认值
     * @param key
     * @param defaultVal
     * @return
     */
    public Object getObjectOrElse(String key,Object defaultVal){
        if(json.has(key)){
            return json.get(key);
        }else {
            return defaultVal;
        }
    }
}
