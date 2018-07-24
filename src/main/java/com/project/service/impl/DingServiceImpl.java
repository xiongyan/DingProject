package com.project.service.impl;

import com.project.model.App;
import com.project.model.Record;
//import com.project.model.RespEntity;
import com.project.service.DingService;
import com.project.util.DingUtil;
import com.project.util.JsonUtil;
import com.project.util.ResultUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("dingService")
public class DingServiceImpl implements DingService {

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
            return ResultUtil.error(500,"查询出错");
        }else {
            return ResultUtil.success(jsonToApps(res));
        }
    }

    /**
     * 获取获取打卡记录
     * @return
     */
    public Object getRecordList(int department){
        int code = 500;
        String msg = "查询出错";
        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTime(format2.parse("2018-04-08 00:00:00"));
            long startTime = cal.getTimeInMillis();
            cal.setTime(format2.parse("2018-04-08 23:59:59"));
            long endTime = cal.getTimeInMillis();
            DingUtil dingUtil = DingUtil.getInstance();
            JSONArray res = dingUtil.gitRecordList(department,startTime,endTime);
            if(res == null){
                return ResultUtil.error(500,"查询出错");
            }else {
                return ResultUtil.success(jsonToRecords(res));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 由于序列化，需要将JSONArray转成List<Record>
     * @param array
     * @return
     */
    public List<Record> jsonToRecords(JSONArray array){
        List<Record> list = null;
        if(array != null){
            JsonUtil obj = new JsonUtil(new JSONObject());
            list = new ArrayList<>();
            for(int i = 0;i<array.length();i++){
                JsonUtil tmp = new JsonUtil((JSONObject)array.get(i));
                if(!obj.getStringOrElse("userId").equalsIgnoreCase(tmp.getStringOrElse("userId"))){
                    Record record = new Record();
                    record.setDetailPlace(tmp.getStringOrElse("detailPlace"));
                    record.setLatitude(tmp.getDoubbleOrElse("latitude",0.0));
                    record.setName(tmp.getStringOrElse("name"));
                    record.setRemark(tmp.getStringOrElse("remark"));
                    record.setPlace(tmp.getStringOrElse("place"));
                    record.setUserId(tmp.getStringOrElse("userId"));
                    record.setTimestamp(tmp.getLongOrElse("timestamp",0));
                    record.setLongitude(tmp.getDoubbleOrElse("longitude", 0.0));
                    JSONArray arys = tmp.getJSONArray("imageList", null);
                    if(arys.length() > 0){
                        List<String> listImage = new ArrayList<>();
                        for(int j= 0;j<arys.length();j++){
                            listImage.add(arys.getString(j));
                        }
                        record.setImageList(listImage);
                    }
                    list.add(record);
                    obj = tmp;
                }
            }
        }
        return list;
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
