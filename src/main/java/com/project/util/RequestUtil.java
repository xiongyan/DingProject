package com.project.util;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Created by laishun on 2018/3/15.
 */
@Component
public class RequestUtil {
    private BufferedReader reader;
    private String contentStr="";

    /**
     * 获取reques中body的数据
     * @param req
     * @return
     */
    public JSONObject getBody(HttpServletRequest req){
        JSONObject result = null;
        try{
            reader = new BufferedReader(new InputStreamReader(req.getInputStream()));;
            contentStr= IOUtils.toString(reader);
            result = new JSONObject(contentStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}


