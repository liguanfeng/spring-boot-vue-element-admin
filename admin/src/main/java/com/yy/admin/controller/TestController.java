package com.yy.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping("/test")
    public Result test(Page page){
        return new Result<>(page);
    }
}
