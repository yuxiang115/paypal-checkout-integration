package com.javatechie.spring.paypal.api.advice;

import com.javatechie.spring.paypal.api.response.ResultBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultBean exceptionHandler(Exception exception){
        String errorMsg = exception.getLocalizedMessage();
        if(errorMsg == null || errorMsg.isEmpty()){
            errorMsg = "Server error";
        }
        return new ResultBean(500, errorMsg);
    }
}
