package com.yy.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.dao.RoleMapper;
import com.yy.admin.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    public List<Role> getList(Page page, String name) {
        roleMapper.selectPage(page, new QueryWrapper<Role>()
                .like(name != null, "name", name));
        return page.getRecords();
    }

    public void delete(Integer id) {
        roleMapper.deleteById(id);
    }

    public void save(Role role) {
        Assert.hasText(role.getName(), "名称不能为空");
        if (role.getId() == null) {
            role.setCreateTime(LocalDateTime.now());
            role.insert();
        } else {
            role.updateById();
        }
    }

}
