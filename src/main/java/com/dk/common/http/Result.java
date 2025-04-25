package com.dk.common.http;

import com.alibaba.fastjson.JSON;

/**
 * Created by cjq on 2018/3/31.
 * 服务处理结果父类
 */
public class Result {

    public static final int OK = 0;

    /**
     * 响应代码
     */
    private int code = 0;
    /**
     * 响应消息
     */
    private String msg = "success";

    public Result(){

    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public static Result init(){
        return new Result(OK,"SUCCESS");
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
