package com.dk.interceptor;

import com.dk.common.exception.MyException;
import com.dk.common.exception.MyParamException;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(HttpServletResponse response, NullPointerException ex){
        logger.error("NullPointerException {}",ex.getMessage());
        logger.error("NullPointerException",ex);
        Result result = new Result();
        result.setCode(8000);
        result.setMsg("系统异常");
        return result;
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException ex) {
        logger.error("RuntimeException {}",ex.getMessage());
        logger.error("RuntimeException",ex);
        Result result = new Result();
        result.setCode(8000);
        result.setMsg(ex.getMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception ex) {
        logger.error("Exception: {}",ex.getMessage());
        logger.error("Exception",ex);
        Result result = new Result();
        result.setCode(8000);
        result.setMsg(ex.getMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(MyException.class)
    public Result myException(MyException ex) {
        logger.error("MyException: {} {}",ex.getCode(), ex.getMsg());
        logger.error("MyException",ex);
        Result result = new Result();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMsg());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(MyParamException.class)
    public Result myParamException(MyParamException ex) {
        logger.error("MyParamException: {} {}",ex.getCode(), ex.getMsg());
        logger.error("MyParamException",ex);
        Result result = new Result();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMsg());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(MyServiceException.class)
    public Result myServiceException(MyServiceException ex) {
        logger.error("MyServiceException: {} {}",ex.getCode(), ex.getMsg());
        logger.error("MyServiceException",ex);
        Result result = new Result();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMsg());
        return result;
    }

}
