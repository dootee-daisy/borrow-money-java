package com.dk.common.http;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by cjq on 2018/4/12.
 */
public class Request {


    private JSONObject param;

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    //    private Map<String,Object> params;
//
//    public Map<String, Object> getParams() {
//        if (null!=params){
//            for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();){
//                Map.Entry<String, Object> item = it.next();
//                Object value = item.getValue();
//                if (null == value){
//                    it.remove();
//                }else if (value instanceof String && StringUtils.trimAllWhitespace((String) value).isEmpty()){
//                    it.remove();
//                }
//            }
//        }
//        return params;
//    }
//
//    public void setParams(Map<String, Object> params) {
//        this.params = params;
//    }
//
//    @Override
//    public String toString() {
//        return "Request{" +
//                "params=" + params +
//                '}';
//    }
//
//    public static Map<String,Object> convertParams(Map<String,Object> params,String[] contain){
//        Map<String,Object> queryMap = new HashMap<>();
//        if (null==params || params.isEmpty()){
//            return  queryMap;
//        }
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            String key = entry.getKey();
//            if (null!=contain && Arrays.asList(contain).contains(key)){
//                StringBuffer sb = new StringBuffer();
//                for (int i =0;i<key.length();i++){
//                    char chr = key.charAt(i);
//                    if (Character.isUpperCase(chr)){
//                        sb.append("_").append(Character.toLowerCase(chr));
//                    }else {
//                        sb.append(chr);
//                    }
//                }
//                key = sb.toString();
//                queryMap.put(key,entry.getValue());
//            }else{
//                queryMap.put(key,entry.getValue());
//            }
//        }
//        return queryMap;
//    }
}
