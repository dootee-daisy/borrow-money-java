package com.dk.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.redis.RedisKeyPrefix;
import com.dk.common.redis.RedisService;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthorityHelper {
    private int MAX_AGE = 30*60*24;

    @Autowired
    private RedisService redisService;

    public void login(HttpServletResponse response, JSONObject object){
        String token = UUID.randomUUID().toString().replace("-","");
        object.put("token",token);
        redisService.addDefaultAgeValue(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token,object);
        Cookie cookie = new Cookie("token", token);
        //12小时
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void logout(String token){
        if (StringUtils.isEmpty(token)){
            redisService.deleteKey(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token);
        }
    }

    public boolean verify(HttpServletRequest request){
        String token = request.getHeader(Constant.HTTP_HEADER_TOKEN);
        if (StringUtils.isEmpty(token)){
            token = getToken(request);
        }
        if (StringUtils.isEmpty(token)){
            return false;
        }
        JSONObject object = redisService.getValue(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token,JSONObject.class);
        if (null!=object){
            ConcurrentHashMap<String, String> headers = new ConcurrentHashMap<>();
            headers.put(Constant.HTTP_HEADER_ID,object.getString("id"));
            headers.put(Constant.HTTP_HEADER_TYPE,object.getString("type"));
            headers.put(Constant.HTTP_HEADER_TOKEN,token);
            modifyHeaders(headers,request);
            return true;
        }
        return false;
    }

    private String getToken(HttpServletRequest request){
        String value  = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(Constant.HTTP_HEADER_TOKEN)){
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    private String getValue(HttpServletRequest request,String name){
        String value  = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(name)){
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    private void modifyHeaders(Map<String, String> headerses, HttpServletRequest request) {
        if (headerses == null || headerses.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            for (Map.Entry<String, String> entry : headerses.entrySet()) {
                o2.removeHeader(entry.getKey());
                o2.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

}
