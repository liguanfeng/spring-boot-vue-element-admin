package com.yy.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.admin.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.admin.vo.AdminVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台管理员 Mapper 接口
 * </p>
 *
 * @author liguanfeng
 * @since 2020-05-17
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 获取管理员列表
     *
     * @param name
     * @return
     */
    List<AdminVo> getAdminList(Page page, @Param("name") String name);

}
