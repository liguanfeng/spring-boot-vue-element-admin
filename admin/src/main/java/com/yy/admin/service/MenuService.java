package com.yy.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yy.admin.dao.MenuMapper;
import com.yy.admin.entity.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取管理员菜单列表
     *
     * @return
     */
    public List<Menu> getAdminMenuList() {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<>());
        return getChildren(null, menus);
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    public List<Menu> getList(String title) {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<>());
        List<Menu> matchList = menus.stream().filter(menu ->
                isMatchTitle(menu.getTitle(), title) ||
                        isMatchTitle(menu.getComponent(), title) ||
                        isMatchTitle(menu.getName(), title) ||
                        isMatchTitle(menu.getPath(), title)
        )
                .collect(Collectors.toList());
        List<Integer> ids = matchList.stream().map(Menu::getId).collect(Collectors.toList());
        HashSet<Integer> matchIds = new HashSet<>();
        matchIds.addAll(ids);
        getParentIds(menus, ids, matchIds);
        return getChildren(null, menus.stream().filter(item -> matchIds.contains(item.getId())).collect(Collectors.toList()));
    }


    public boolean isMatchTitle(String content, String title) {
        if (content == null) {
            return false;
        }
        return content.contains(title);
    }

    public void getParentIds(List<Menu> menus, List<Integer> ids, Set<Integer> matchIds) {
        List<Integer> parentIds = menus.stream().filter(menu -> ids.contains(menu.getId())).map(Menu::getParentId).collect(Collectors.toList());
        if (!parentIds.isEmpty()) {
            matchIds.addAll(parentIds);
            getParentIds(menus, parentIds, matchIds);
        }
    }

    /**
     * 获取子节点
     *
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


    public void delete(Integer id) {
        menuMapper.deleteById(id);
        List<Integer> pidList = Arrays.asList(id);
        while (true) {
            List<Menu> children = menuMapper.selectList(new QueryWrapper<Menu>().in("parentId", pidList));
            if (children.isEmpty()) {
                break;
            }
            pidList = children.stream().map(Menu::getId).collect(Collectors.toList());
            menuMapper.delete(new UpdateWrapper<Menu>().in("id", pidList));
        }
    }
}
