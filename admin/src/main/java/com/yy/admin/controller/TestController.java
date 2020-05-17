package com.yy.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test")
    public Result test(Page page){
        return new Result<>(page);
    }
}
