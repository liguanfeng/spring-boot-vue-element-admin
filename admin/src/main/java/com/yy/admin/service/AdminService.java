package com.yy.admin.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.dao.AdminMapper;
import com.yy.admin.entity.Admin;
import com.yy.admin.util.JwtUtil;
import com.yy.admin.vo.AdminVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
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
        Assert.hasText(name, "账号不能为空");
        Assert.hasText(password, "密码不能为空");
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", name)
                .eq("password", SecureUtil.md5(password)));
        Assert.notNull(admin, "账号或密码错误");
        Assert.isTrue(admin.getIsEnable(), "账号已经被锁定,请联系管理员");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
//        String token = UUID.randomUUID().toString().replace("-", "");
        String token = JwtUtil.createToken(admin);
        adminVo.setToken(token);
        return adminVo;
    }

    /**
     * 管理员账号列表
     *
     * @param page
     * @param name
     * @return
     */
    public List<AdminVo> getList(Page page, String name) {
        List<AdminVo> adminList = adminMapper.getAdminList(page, name);
        page.setRecords(adminList);
        return adminList;
    }


    /**
     * 删除
     *
     * @param id
     */
    public void delete(Integer id) {
        Admin admin = adminMapper.selectById(id);
        Assert.isTrue(!admin.getIsMaster(), "账号不允许删除");
        admin.deleteById(id);
    }


    /**
     * 修改密码
     *
     * @param id
     * @param password
     */
    public void updatePassword(Integer id, String password) {
        Assert.notNull(id, "id不能为空");
        Assert.hasText(password, "密码不能为空");
        Admin admin = adminMapper.selectById(id);
        Assert.isTrue(!admin.getIsMaster(), "账号不允许操作");
        adminMapper.update(null, new UpdateWrapper<Admin>()
                .set("password", SecureUtil.md5(password))
                .eq("id", id));

    }

    public void updateEnable(Integer id, boolean isEnable) {
        Assert.notNull(id, "id不能为空");
        Assert.notNull(isEnable, "启用状态不能为空");
        Admin admin = adminMapper.selectById(id);
        Assert.isTrue(!admin.getIsMaster(), "账号不允许操作");
        adminMapper.update(null, new UpdateWrapper<Admin>()
                .set("isEnable", isEnable)
                .eq("id", id));

    }


    /**
     * 新增或修改
     *
     * @param admin
     */
    public void save(Admin admin) {
        Assert.hasText(admin.getRealName(), "真实姓名");
        Assert.hasText(admin.getName(), "登录账号不能为空");
        admin.setIsEnable(admin.getIsEnable() == null ? true : admin.getIsEnable());
        if (admin.getId() != null) {
            admin.setIsMaster(null);
            admin.setCreateTime(null);
            admin.setPassword(null);
            admin.updateById();
        } else {
            Assert.hasText(admin.getPassword(), "密码不能为空");
            admin.setIsMaster(false);
            admin.setCreateTime(LocalDateTime.now());
            admin.setPassword(SecureUtil.md5(admin.getPassword()));
            admin.insert();
        }
    }


}
