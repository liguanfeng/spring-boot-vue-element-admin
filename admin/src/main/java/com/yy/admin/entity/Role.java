package com.yy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liguanfeng
 * @since 2020-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role")
@ApiModel(value="Role对象", description="")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "拥有的菜单与权限")
    private String menuIds;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
