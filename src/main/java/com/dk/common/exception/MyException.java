package com.dk.common.exception;

public class MyException extends Exception {

    public static final int UNKNOWN_ERROR_CODE = 8000;
    public static final String UNKNOWN_ERROR_MSG = "System exception, please try again later";

    private int code = 200;
    private String msg;


    public MyException(){

    }

    public MyException(int code,String msg){
        this.code = code;
        this.msg = msg;
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
}
