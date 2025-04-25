package com.dk.common.http;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by cjq on 2018/3/31.
 */
public class DataResult<T> extends Result{

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data instanceof Collection){
            JSONObject object = new JSONObject();
            object.put("items",data);
            this.data = (T) object;
        }else {
             this.data = data;
        }
    }
    public DataResult(int code,String msg) {
        super(code,msg);
    }

    public DataResult(int code) {
        super(code);
    }

    public static DataResult init(){
        return new DataResult(OK,"SUCCESS");
    }
    public DataResult(){

    }
    public DataResult buildData(T data){
        if (data instanceof Collection){
            JSONObject object = new JSONObject();
            object.put("items",data);
            this.data = (T) object;
        }else {
            this.data = data;
        }
        return this;
    }
}
