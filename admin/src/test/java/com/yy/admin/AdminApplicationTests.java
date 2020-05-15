package com.yy.admin;

import com.alibaba.fastjson.JSON;
import com.yy.admin.entity.Menu;
import com.yy.admin.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdminApplicationTests {


    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        List<Menu> list = menuService.getList(null);
        System.out.println(JSON.toJSONString(list,true));
    }

}
