package com.project.filter;

import org.json.JSONObject;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by laishun on 2018/3/9.
 */
@Order(1)
@WebFilter(filterName = "AdminFilter", urlPatterns = "/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
   /* public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = AdminFilter.setCros((HttpServletResponse) servletResponse);
        if(!req.getMethod().equalsIgnoreCase("OPTIONS")){
            String path = req.getRequestURI();
            if(path.contains("/login")){
                filterChain.doFilter(req, resp);
            }else {
                User user = CacheUtil.getInstance().getUser(req);
                if(user != null){
                    *//*int admin = user.getAdmin();
                    if(path.startsWith("/admin")){
                        if(admin == 1){
                            filterChain.doFilter(req, resp);
                        }else{
                            AdminFilter.filterUtil(resp, "您没有访问此模块的权限，如果需要访问，请联系管理员", 403);
                        }
                    }else{
                        filterChain.doFilter(req, resp);
                    }*//*
                    filterChain.doFilter(req, resp);
                }else {
                    AdminFilter.filterUtil(resp, "您尚未登录，请先登录!", 403);
                }
            }
        }else{
            filterChain.doFilter(req, resp);
        }
    }*/

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = AdminFilter.setCros((HttpServletResponse) servletResponse);
        if(!req.getMethod().equalsIgnoreCase("OPTIONS")){
            String path = req.getRequestURI();
            if(path.contains("/login")){
                filterChain.doFilter(req, resp);
            }else {
                filterChain.doFilter(req, resp);
            }
        }else{
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 用来返回给前端信息
     * @param resp
     * @param res
     * @param code
     * @throws IOException
     * @throws ServletException
     */
    public static void filterUtil(HttpServletResponse resp,String res,int code) throws IOException, ServletException{
        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("msg",res);
        resp.setHeader("Cache-Control", "no-store");
        resp.setDateHeader("Expires", 0);
        resp.setHeader("Prama", "no-cache");
        resp.setHeader("Content-type", "application/json;charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(result.toString());
    }

    /**
     * 设置跨越
     * @param resp
     * @return
     */
    public static HttpServletResponse setCros(HttpServletResponse resp){
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*, X-Requested-With, Content-Type,token");
        return resp;
    }
}
