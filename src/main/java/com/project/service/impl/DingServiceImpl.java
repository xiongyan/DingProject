package com.project.service.impl;

import com.project.model.App;
import com.project.model.RespEntity;
import com.project.service.DingService;
import com.project.util.DingUtil;
import com.project.util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("dingService")
public class DingServiceImpl implements DingService {

    @Resource
    private RespEntity respEntity;
    /**
     * 获取第三方应用
     * @return
     */
    public Object getThirdAppList(){
        int code = 500;
        String msg = "查询出错";
        DingUtil dingUtil = DingUtil.getInstance();
        JSONArray res = dingUtil.getThirdAppList();
        if(res == null){
            respEntity.setData(null);
        }else {
            code = 200;
            msg = "查询成功";
            respEntity.setData(jsonToApps(res));
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 由于序列化，需要将JSONArray转成List<App></>
     * @param array
     * @return
     */
    public List<App> jsonToApps(JSONArray array){
        List<App> list = null;
        if(array != null){
            list = new ArrayList<>();
            for(int i = 0;i<array.length();i++){
                JsonUtil obj = new JsonUtil((JSONObject)array.get(i));
                App app = new App();
                app.setAppIcon(obj.getStringOrElse("appIcon"));
                app.setAgentId(obj.getIntOrElse("agentId", 0));
                app.setAppDesc(obj.getStringOrElse("appDesc"));
                app.setPcHomepageLink(obj.getStringOrElse("pcHomepageLink"));
                app.setName(obj.getStringOrElse("name"));
                app.setHomepageLink(obj.getStringOrElse("homepageLink"));
                app.setAppStatus(obj.getIntOrElse("appStatus", 0));
                app.setIsSelf(obj.getBooleanOrElse("isSelf",false));
                app.setOmpLink(obj.getStringOrElse("ompLink"));
                list.add(app);
            }

        }
        return list;
    }
}
