package com.yy.admin.vo;

import com.yy.admin.entity.Admin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AdminVo extends Admin {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("角色名称")
    private String roleName;


}
