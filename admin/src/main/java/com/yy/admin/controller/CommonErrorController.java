package com.yy.admin.controller;


import com.alibaba.fastjson.JSON;
import com.yy.admin.exception.ResponseException;
import com.yy.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class CommonErrorController extends BasicErrorController {

    private ErrorAttributes errorAttributes;

    public CommonErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }

        String message;
        Integer code = status.value();
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        message = (String) body.get("message");
        String path = (String) body.get("path");
        switch (status.value()) {
            case 404:
                message = "请求接口不存在: " + path;
                break;
            case 400:
                message = "请求参数类型错误 ";
                break;
            case 405:
                message = "请求方法不支持 ";
                break;
        }
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));
        if (error != null && error instanceof ResponseException) {
            code = ((ResponseException) error).getCode();
            message = error.getMessage();
        }
        body.put("message", message);
        body.put("code", code);
        return new ResponseEntity<>(body, status);
    }

}
