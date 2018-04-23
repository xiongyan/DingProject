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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        result.put("jsticket",ticket);
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
                    Map.Entry e = (Map.Entry)var2.next();
                    Object value = e.getValue();
                    if(value != null) {
                        result.put(String.valueOf(e.getKey()), wrap(value));
                    }
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
            respEntity.setCode(200);
            respEntity.setMsg("用户登陆成功");
        }else{
            respEntity.setCode(500);
            respEntity.setMsg("用户名或密码错误");
            respEntity.setData(null);
        }
        return  respEntity;
    }

    @RequestMapping(value = "/logout", method= RequestMethod.POST)
    public Object logout(HttpServletRequest req) {
        // 移除session
        String token_access = req.getHeader("token");
        User user = (User)CacheUtil.getInstance().get(token_access);
        CacheUtil.getInstance().remove(token_access);
        CacheUtil.getInstance().remove(user.getPhone());
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
            } else if(!(object instanceof JSONObject) && !(object instanceof JSONArray) && !(object instanceof JSONString) && !(object instanceof Byte) && !(object instanceof Character) && !(object instanceof Short) && !(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Boolean) && !(object instanceof Float) && !(object instanceof Double) && !(object instanceof String) && !(object instanceof BigInteger) && !(object instanceof BigDecimal)) {
                if(object instanceof Collection) {
                    Collection exception2 = (Collection)object;
                    return new JSONArray(exception2);
                } else if(object.getClass().isArray()) {
                    return new JSONArray(object);
                } else if(object instanceof Map) {
                    Map exception1 = (Map)object;
                    return new JSONObject(exception1);
                } else {
                    Package exception = object.getClass().getPackage();
                    String objectPackageName = exception != null?exception.getName():"";
                    return !objectPackageName.startsWith("java.") && !objectPackageName.startsWith("javax.") && object.getClass().getClassLoader() != null?new JSONObject(object):object.toString();
                }
            } else {
                return object;
            }
        } catch (Exception var3) {
            return null;
        }
    }
}
