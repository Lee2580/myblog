package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 状态vo
 *      用于user、role、menu修改状态
 * @author lee
 * @create 2021-09-16 14:04
 **/
@Data
public class StatusVo {

    /**
     * id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(name = "id", value = "用户/角色/菜单/id", dataType = "Long")
    private Long id;

    /**
     * 禁用状态
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(name = "status", value = "用户状态", dataType = "Integer")
    private Integer status;

}
