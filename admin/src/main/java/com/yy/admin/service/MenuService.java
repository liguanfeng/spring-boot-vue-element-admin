package com.yy.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yy.admin.dao.MenuMapper;
import com.yy.admin.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.isEmpty(title)) {
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
            menus = menus.stream().filter(item -> matchIds.contains(item.getId())).collect(Collectors.toList());
        }

        return getChildren(null, menus);
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


    /**
     * 删除菜单
     *
     * @param id
     */
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

    /**
     * 新增或修改菜单
     *
     * @param menu
     */
    public void save(Menu menu) {
        Assert.hasText(menu.getTitle(), "菜单名称不能为空");
        Assert.hasText(menu.getPath(), "菜单路径不能为空");
        Assert.notNull(menu.getIsApi(), "请选择创建菜单或权限");
        menu.setHidden(menu.getHidden() == null ? false : menu.getHidden());
        if (menu.getIsApi()) {
            Assert.notNull(menu.getParentId(), "权限必须选择上级菜单");
        }
        if (menu.getId() == null) {
            menu.insert();
        } else {
            if (menu.getId().equals(menu.getParentId())) {
                menu.setParentId(null);
            }
            menu.update(new UpdateWrapper<Menu>()
                    .set("parentId", menu.getParentId())
                    .eq("id", menu.getId())
            );
        }

    }
}
