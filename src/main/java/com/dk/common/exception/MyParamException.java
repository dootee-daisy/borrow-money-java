package com.dk.common.exception;

public class MyParamException extends MyException {

    public MyParamException(){

    }
    public MyParamException(int code,String msg){
        super(code,msg);
    }

    public MyParamException(String msg){
        super(7010,msg);
    }
}
