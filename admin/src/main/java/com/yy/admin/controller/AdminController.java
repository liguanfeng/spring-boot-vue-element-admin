package com.yy.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.entity.Admin;
import com.yy.admin.service.AdminService;
import com.yy.admin.service.MenuService;
import com.yy.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private MenuService menuService;

    /**
     * 登录
     *
     * @param name
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(String name, String password) {
        return new Result(adminService.login(name, password));
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        return new Result();
    }


    /**
     * 菜单列表
     *
     * @return
     */
    @RequestMapping("/menus")
    public Result menus() {
        return new Result(menuService.getAdminMenuList());
    }

    @RequestMapping("/list")
    public Result list(Page page, String name) {
        adminService.getList(page, name);
        return new Result(page);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        adminService.delete(id);
        return new Result();
    }

    @RequestMapping("/save")
    public Result save(Admin admin) {
        adminService.save(admin);
        return new Result();
    }

    @RequestMapping("/updatePassword")
    public Result updatePassword(Integer id, String password) {
        adminService.updatePassword(id, password);
        return new Result();
    }

    @RequestMapping("/updateEnable")
    public Result updateEnable(Integer id, Boolean isEnable) {
        adminService.updateEnable(id, isEnable);
        return new Result();
    }

}
