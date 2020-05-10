package com.yy.admin.controller;


import com.yy.admin.vo.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController implements ErrorController {


    @RequestMapping("/error")
    public Result error() {
        return new Result("服务器异常", 500);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
