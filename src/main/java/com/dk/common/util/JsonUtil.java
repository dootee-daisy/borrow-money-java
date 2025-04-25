package com.dk.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class JsonUtil<T> {
    public static<T> List<T> arrayToList(JSONArray array, Class clazz){
        if (null != array){
           return JSONObject.parseArray(array.toJSONString(),clazz);
        }
        return null;
    }
}
