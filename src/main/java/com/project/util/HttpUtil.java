package com.project.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by laishun on 2018/4/4.
 */
public class HttpUtil {

    /**
     * post request
     * @param url
     * @param body
     * @return
     */
    public String post(String url,String body){
        HttpResponse rep = null;
        String result = "";
        try {
            HttpClient httpClients = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(body,"UTF-8");
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);
            rep = httpClients.execute(httpPost);
            //如果没发送成功 尝试再次发送
            if(rep.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                int flag = 0;
                while (flag < 2) {
                    rep = httpClients.execute(httpPost);
                    if (rep.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        flag = 2;
                    } else {
                        flag = flag + 1;
                    }
                }
            }
            HttpEntity resEntity = rep.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity,"utf-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get request
     * @param url
     * @return
     */
    public String get(String url){
        HttpResponse rep = null;
        String result = "";
        try{
            HttpClient httpClients = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            rep = httpClients.execute(httpGet);
            HttpEntity resEntity = rep.getEntity();
            if(resEntity != null){
                //处理乱码问题
                result = EntityUtils.toString(resEntity,"utf-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String args[]){
        String url = "https://oapi.dingtalk.com/department/list?access_token=47140582c6c23bc6ab03adf4a574ab9e";
        //HttpUtil httpUtil = new HttpUtil();
        //System.out.println(httpUtil.get(url));
    }
}
