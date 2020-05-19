package com.yy.admin.controller;

import com.yy.admin.AdminApplication;
import com.yy.admin.annotations.Authorization;
import com.yy.admin.entity.Menu;
import com.yy.admin.service.MenuService;
import com.yy.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 菜单列表
     *
     * @return
     */
    @RequestMapping("/request_path_list")
    @Authorization(checkPrivilege = false)
    public Result apiPathList(String path) {
        String basePack = AdminApplication.class.getPackage().getName();
        Collection<String> pathList = new TreeSet<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            Set<String> patterns = k.getPatternsCondition().getPatterns();
            patterns.forEach(pattern -> {
                Authorization auth = v.getMethodAnnotation(Authorization.class);
                if (v.getBeanType().getName().startsWith(basePack) && (auth == null || auth.checkPrivilege())) {
                    pathList.add(pattern);
                }
            });
        });
        if (!StringUtils.isEmpty(path)) {
            List<String> list = pathList.stream().filter(item -> item.contains(path) || PatternMatchUtils.simpleMatch(path, item)).collect(Collectors.toList());
            return new Result(list);
        }
        return new Result(pathList);
    }

    /**
     * 菜单列表
     *
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
