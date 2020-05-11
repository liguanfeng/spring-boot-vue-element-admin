package com.yy.admin.controller;

import com.yy.admin.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
public class ApiExceptionHandler{


//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
    public Result handleServiceException(Exception e, HttpServletRequest request,
                                       HttpServletResponse response) {
       return new Result(e.getMessage(),500);
    }

}
