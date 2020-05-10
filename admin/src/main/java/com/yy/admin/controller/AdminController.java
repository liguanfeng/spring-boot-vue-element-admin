package com.yy.admin.controller;

import com.yy.admin.entity.Admin;
import com.yy.admin.service.AdminService;
import com.yy.admin.vo.AdminVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(String name,String password){
        Admin admin = adminService.login(name,password);
        return admin;
    }

}
