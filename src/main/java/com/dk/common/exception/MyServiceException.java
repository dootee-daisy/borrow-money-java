package com.dk.common.exception;

public class MyServiceException extends MyException {


    public MyServiceException(){

    }
    public MyServiceException(int code, String msg){
        super(code,msg);
    }

    public MyServiceException(String msg){
        super(7000,msg);
    }
}
