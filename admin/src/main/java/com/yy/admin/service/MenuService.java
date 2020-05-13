package com.yy.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.admin.dao.MenuMapper;
import com.yy.admin.entity.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取菜单列表
     * @return
     */
    public List<Menu> getList() {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<>());
        return getChildren(null,menus);
    }

    /**
     * 获取子节点
     * @param parent
     * @param all
     * @return
     */
    private List<Menu> getChildren(Menu parent, List<Menu> all) {
        List<Menu> menus = null;
        if (parent == null) {
            menus = all.stream().filter(item -> item.getParentId() == null).collect(Collectors.toList());
            menus.forEach(item -> getChildren(item, all));
        } else {
            List<Menu> children = all.stream().filter(item -> item.getParentId() == parent.getId()).collect(Collectors.toList());
            parent.setChildren(children);
            children.forEach(item -> getChildren(item, all));
        }
        return menus;
    }


}
