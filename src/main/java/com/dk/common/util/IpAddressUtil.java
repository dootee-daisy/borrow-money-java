package com.dk.common.util;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.http.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Component
public class IpAddressUtil {

    @Autowired
    private RestTemplate restTemplate;

    public String getAddressFromTaobao(String ip){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ip", ip);
        params.add("accessKey", "alibaba-inc");
        DataResult result = restTemplate.postForObject("http://ip.taobao.com/outGetIpInfo",params, DataResult.class);
        if (result.getCode() == 0){
            LinkedHashMap object = (LinkedHashMap) result.getData();
            return object.get("region").toString();
        }else {
            return null;
        }
    }

    public String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
