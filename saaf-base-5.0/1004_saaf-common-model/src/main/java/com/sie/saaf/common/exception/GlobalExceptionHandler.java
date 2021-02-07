package com.sie.saaf.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler{
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({BusinessException.class,PermissionException.class})
    @ResponseBody
    public ErrorInfo<String>ajaxException(HttpServletRequest req,Exception ex){
        ErrorInfo<String> errInfo = new ErrorInfo<String>();
        errInfo.setCode(ErrorInfo.ERROR);
        errInfo.setMsg(ex.getMessage());
        errInfo.setUrl(req.getRequestURI().toString());
        errInfo.setData("some data");
        errInfo.setStatus(ex instanceof PermissionException?"T":"E");
        return errInfo;
    }
}