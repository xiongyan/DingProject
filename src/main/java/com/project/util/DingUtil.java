package com.project.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Formatter;


/**
 * Created by laishun on 2018/4/4.
 */
public class DingUtil {

    public static String CORPID = "ding31148f160c24897635c2f4657eb6378f"; // 企业Id
    public static String CORPSECRET = "7RzFBvHzogkyN2NZ8jii8dyNrWHNCfJiKIHNnNrolWOARMuYfu9o9_YcsQ6jEQah"; // 企业应用的凭证密钥
    public static Long   AGENTID = 169284840L;//微应用的ID
    private static DingUtil dingUtil = null;
    private static HttpUtil httpUtil = null;

    private DingUtil(){
        httpUtil = new HttpUtil();
    }

    /**
     * 单列模式通过一个方法获取
     * @return
     */
    public static DingUtil getInstance(){
        if(dingUtil == null){
            dingUtil = new DingUtil();
        }
        return dingUtil;
    }

    /**
     * @Description:获得token
     * @Method:getToken
     * @param corpid 企业Id
     * @param corpsecret 企业应用的凭证密钥
     */
    private String getToken(String corpid, String corpsecret){
        try {
            String result = httpUtil.get("https://oapi.dingtalk.com/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret);
            JSONObject json = new JSONObject(result);
            if ("0".equals(json.get("errcode").toString())) {
                return json.get("access_token").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * 向网页请求ticket值，用Get方式请求网页
    * param:
    *   access_token:上面得到的access_token值
    * return:
    *   返回值是ticket
    */
    public String getTicket(){
        String token = getToken(CORPID,CORPSECRET);
        HttpUtil httpUtil = new HttpUtil();
        String url="https://oapi.dingtalk.com/get_jsapi_ticket?access_token="+token;
        JSONObject res=new JSONObject(httpUtil.get(url));
        String ticket="";
        if(res.has("ticket")){
            ticket=res.getString("ticket");
        }
        return ticket;
    }

    /**
     * 根据usierid获取用户信息
     * @param userId
     * @return
     */
    public JSONObject getUser(String userId){
        String token = getToken(CORPID,CORPSECRET);
        HttpUtil httpUtil = new HttpUtil();
        String url="https://oapi.dingtalk.com/user/get?access_token="+token+"&userid=zhangsan"+userId;
        JSONObject res=new JSONObject(httpUtil.get(url));
        if(res.getInt("errcode") == 0){
            return res;
        }else{
            return null;
        }
    }

    /**
    * 生成签名的函数
    * params:
    *   ticket:签名数据
    *   nonceStr:签名用的随机字符串，从properties文件中读取
    *   timeStamp:生成签名用的时间戳
    *   url:当前请求的URL地址
    */
    public String getSign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "×tamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");    //安全hash算法
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));         //根据参数产生hash值
            return bytesToHex(sha1.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将bytes类型的数据转化为16进制类型
     */
    private static String bytesToHex(byte[] hash) {                    //将字符串转化为16进制的数据
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    /**
     * @Description:发送消息
     * @Method:sendDDMessage
     * @param userId 接收者的用户userid列表
     * @param  msgcontent 消息内容
     */
    public String sendDDMessage(String userId, String msgcontent) {
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
        req.setMsgtype("oa"); // 消息类型
        req.setAgentId(AGENTID);
        req.setUseridList(userId);
        req.setToAllUser(false); // 是否发送给企业全部用户
        req.setMsgcontentString(msgcontent);
        try {
            String token = getToken(CORPID,CORPSECRET);
            CorpMessageCorpconversationAsyncsendResponse rsp = client.execute(req, token);
            return rsp.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向dingding创建部门并且存放到数据库中
     * @return
     */
    public int addDingApartment(String title){
        int result = -1;
        try {
            String token = getToken(CORPID,CORPSECRET);
            HttpUtil httpUtil = new HttpUtil();
            //先判断这个部门是否存在
            String findUrl = "https://oapi.dingtalk.com/department/list?access_token="+token;
            JSONObject json = new JSONObject(httpUtil.get(findUrl));
            if(json.has("department")){
                JSONArray array = json.getJSONArray("department");
                for(int i=0;i< array.length();i++){
                    JSONObject js = (JSONObject)array.get(i);
                    if(title.equalsIgnoreCase(js.getString("name"))){
                        result = js.getInt("id");
                        return result;
                    }
                }
            }
            String url = "https://oapi.dingtalk.com/department/create?access_token="+token;
            String body = httpUtil.post(url,"{\"name\":\""+title+"\",\"parentid\":\"1\"}");
            JSONObject res = new JSONObject(body);
            result = res.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取部门详情
     * @return
     */
    public JSONObject getApartment(int id){
        JSONObject result = new JSONObject();
        try {
            String token = getToken(CORPID,CORPSECRET);
            HttpUtil httpUtil = new HttpUtil();
            //先判断这个部门是否存在
            String findUrl = "https://oapi.dingtalk.com/department/get?access_token="+token+"&id="+id;
            result = new JSONObject(httpUtil.get(findUrl));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取第三方微应用列表
     * @return
     */
    public JSONArray getThirdAppList(){
        JSONArray result = null;
        try {
            String token = getToken(CORPID,CORPSECRET);
            HttpUtil httpUtil = new HttpUtil();
            //先判断这个部门是否存在
            String url = "https://oapi.dingtalk.com/microapp/list?access_token="+token;
            JSONObject res = new JSONObject(httpUtil.post(url, ""));
            if(res.has("errcode") && res.getInt("errcode") == 0){
                result = res.getJSONArray("appList");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param userID
     * @param userName
     * @param apartment
     * @return
     */
    public int addDingUser(String userID,String userName,int apartment){
        int result = 500;
        try {
            String token = getToken(CORPID,CORPSECRET);
            HttpUtil httpUtil = new HttpUtil();
            //判断用户是否存在
            String isExitUrl = "https://oapi.dingtalk.com/user/get?access_token="+token+"&userid="+userID;
            JSONObject res = new JSONObject(httpUtil.get(isExitUrl));
            if(res.getInt("errcode") == 0){
                JSONArray array = res.getJSONArray("department");
                if(!array.toString().contains(""+apartment)){
                    String update = "https://oapi.dingtalk.com/user/update?access_token="+token;
                    array.put(apartment);
                    String updateBody = "{\"userid\": \""+userID+"\",\"name\": \""+userName+"\",\"department\": "+array+",\"mobile\": \""+userID+"\"}";
                    httpUtil.post(update,updateBody);
                }
            }else{
                String url = "https://oapi.dingtalk.com/user/create?access_token="+token;
                String body = "{\"userid\": \""+userID+"\",\"name\": \""+userName+"\",\"department\": ["+apartment+"],\"mobile\": \""+userID+"\"}";
                JSONObject json = new JSONObject(httpUtil.post(url,body));
                result = json.getInt("errcode");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     *获取部门打卡记录
     * @param department
     * @return
     */
    public JSONArray gitRecordList(int department,long startTime,long endTime){
        JSONArray result = null;
        try {
            String token = getToken(CORPID,CORPSECRET);
            HttpUtil httpUtil = new HttpUtil();
            String url = "https://oapi.dingtalk.com/checkin/record?access_token="+token+"&department_id="+department+"&start_time="+startTime+"&end_time="+endTime;
            JSONObject res = new JSONObject(httpUtil.get(url));
            if(res.getInt("errcode") == 0){
                result = res.getJSONArray("data");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String args[]){
        DingUtil dingUtil = DingUtil.getInstance();
        //dingUtil.sendDDMessage("06453041331160676","{\"message_url\": \"http://dingtalk.com\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"交房租提醒消息\"},\"body\": {\"title\": \"交房租\",\"form\": [{\"key\": \"租客:\",\"value\": \"赖顺\"},{\"key\": \"房子:\",\"value\": \"上海浦东新区\"}],\"rich\": {\"num\": \"15.6\",\"unit\": \"元\"},\"content\": \"该交房租啦\",\"image\": \"\",\"file_count\": \"3\",\"author\": \"房东 \"}}");
        dingUtil.addDingUser("15821448530","租客2", 63536117);

    }
}
