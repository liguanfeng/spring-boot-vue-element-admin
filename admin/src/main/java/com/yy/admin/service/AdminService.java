package com.yy.admin.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.admin.dao.AdminMapper;
import com.yy.admin.entity.Admin;
import com.yy.admin.vo.AdminVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;


    /**
     * 账号登录
     *
     * @param name
     * @param password
     * @return
     */
    public Admin login(String name, String password) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", name)
                .eq("password", SecureUtil.md5(password)));
        Assert.notNull(admin, "账号或密码错误");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        String token = UUID.randomUUID().toString().replace("-", "");
        adminVo.setToken(token);
        return admin;
    }
}
