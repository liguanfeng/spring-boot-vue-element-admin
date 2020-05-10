package com.yy.admin.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * js跨域问题
 */
@SuppressWarnings("all")
public class CommonInterceptor extends HandlerInterceptorAdapter {


    public static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//        if(! (response.getStatus() == 200) ){
//            return false;
//        }
        response.setHeader("Pragma", "no-cache");   // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,TOKEN,RequestType,RequestType,*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        REQUEST_ID.set(UUID.randomUUID().toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
