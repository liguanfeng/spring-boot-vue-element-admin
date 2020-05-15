package com.yy.admin.controller;

import com.yy.admin.entity.Menu;
import com.yy.admin.service.MenuService;
import com.yy.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     * @return
     */
    @RequestMapping("/list")
    public Result list(String title) {
        return new Result(menuService.getList(title));
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        menuService.delete(id);
        return new Result();
    }

    @RequestMapping("/save")
    public Result save(Menu menu) {
        menuService.save(menu);
        return new Result();
    }




}
