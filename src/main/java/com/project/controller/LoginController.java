package com.project.controller;

import com.project.model.RespEntity;
import com.project.model.User;
import com.project.service.LoginService;
import com.project.util.CacheUtil;
import com.project.util.DingUtil;
import com.project.util.Md5Util;
import com.project.util.RequestUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private Md5Util md5Util;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity;

    @RequestMapping(value="/sign",method= RequestMethod.GET)
    @ResponseBody
    public Object sign(HttpServletRequest req){
        /**
        *以http://localhost/test.do?a=b&c=d为例
        *request.getRequestURL的结果是http://localhost/test.do
        *request.getQueryString的返回值是a=b&c=d
        */
        String urlString = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        String url;
        if(queryString!=null){
            url=urlString+queryString;
        }else{
            url=urlString;
        }
        String nonceStr="noncestr_ding";
        long timeStamp = System.currentTimeMillis()/1000;
        String ticket = null;
        String signature = null;
        try {
            DingUtil dingUtil = DingUtil.getInstance();
            ticket = dingUtil.getTicket();
            signature = dingUtil.getSign(ticket,nonceStr,timeStamp,url);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,Object> result = new HashMap<>();
        //result.put("jsticket",ticket);
        result.put("signature",signature);
        result.put("nonceStr",nonceStr);
        result.put("timeStamp",timeStamp);
        result.put("corpId",DingUtil.CORPID);
        result.put("agentId",DingUtil.AGENTID);
        return result;
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest req) {
        int code = 500;
        String msg = "没找到该用户";
        //获取用户的登陆信息，并且进行认证
        JSONObject res = requestUtil.getBody(req);
        String userId = res.getString("userId");
        if (userId != null && !"".equalsIgnoreCase(userId)){
            //TODO 根据前端传过来的userId去后台数据查询是否存在
            //User user = loginService.findUserByNameAndPwd(username, password);
            //获取用户的详情
            JSONObject obj = DingUtil.getInstance().getUser(userId);
            Map<String,Object> result = new HashMap<>();
            if(obj != null) {
                Iterator var2 = obj.keySet().iterator();
                while(var2.hasNext()) {
                    String key = (String)var2.next();
                    Object value = wrap(obj.opt(key));
                    if(value != null) {
                        result.put(key, value);
                    }
                }
                //判断是否缓存中有啦
                Object token_old = CacheUtil.getInstance().get(userId);
                if(token_old != null && !"".contentEquals(token_old.toString())){
                    CacheUtil.getInstance().remove(userId);
                    CacheUtil.getInstance().remove(token_old.toString());
                }
                Long loginTime = System.currentTimeMillis();
                String token_access = md5Util.MD5("userId:"+userId+";loginTime:"+loginTime);
                result.put("token", token_access);
                CacheUtil.getInstance().put(userId,token_access);
                CacheUtil.getInstance().put(token_access, obj);
                respEntity.setData(result);
                code = 200;
                msg = "登陆成功";
            }
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    @RequestMapping(value = "/logout", method= RequestMethod.POST)
    public Object logout(HttpServletRequest req) {
        // 移除session
        String token_access = req.getHeader("token");
        JSONObject obj = (JSONObject)CacheUtil.getInstance().get(token_access);
        CacheUtil.getInstance().remove(token_access);
        CacheUtil.getInstance().remove(obj.getString("userid"));
        respEntity.setCode(200);
        respEntity.setMsg("成功退出");
        respEntity.setData(null);
        return respEntity;
    }

    /**
     * 处理object方法
     * @param object
     * @return
     */
    public static Object wrap(Object object) {
        try {
            if(object == null) {
                return null;
            }else if(object instanceof JSONObject) {
                Map<String,Object> result = new HashMap<>();
                JSONObject obj = (JSONObject)object;
                Iterator var2 = obj.keySet().iterator();
                while(var2.hasNext()) {
                    String key = (String)var2.next();
                    Object value = wrap(obj.opt(key));
                    if(value != null) {
                        result.put(key, value);
                    }
                }
                return result;
            } else if(object instanceof JSONArray) {
                List<Object> list = new ArrayList<>();
                JSONArray array = (JSONArray)object;
                Iterator var2 = array.iterator();
                while(var2.hasNext()) {
                    Object obj = wrap(var2.next());
                    if(obj != null) {
                        list.add(obj);
                    }
                }
                return list;
            } else {
                return object;
            }
        } catch (Exception var3) {
            return null;
        }
    }
}
