package com.yy.admin.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.entity.Role;
import com.yy.admin.service.RoleService;
import com.yy.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping("/list")
    public Result list(ServletRequest request, Page page, String name) {
        System.out.println(request.getParameter("size"));
        System.out.println(request.getParameter("current"));
        roleService.getList(page, name);
        return new Result(page);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        roleService.delete(id);
        return new Result();
    }

    @RequestMapping("/save")
    public Result save(Role role) {
        roleService.save(role);
        return new Result();
    }
}
