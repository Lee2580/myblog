package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单VO
 * @author lee
 * @create 2021-09-14 16:20
 **/
@Data
public class MenuVo {

    /**
     * id
     */
    @ApiModelProperty(name = "menuId", value = "菜单id", dataType = "Long")
    private Long menuId;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    @ApiModelProperty(name = "name", value = "菜单名", dataType = "String")
    private String name;

    /**
     * icon
     */
    @NotBlank(message = "菜单icon不能为空")
    @ApiModelProperty(name = "icon", value = "菜单icon", dataType = "String")
    private String icon;

    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空")
    @ApiModelProperty(name = "path", value = "路径", dataType = "String")
    private String path;

    /**
     * 组件
     */
    @NotBlank(message = "组件不能为空")
    @ApiModelProperty(name = "component", value = "组件", dataType = "String")
    private String component;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @ApiModelProperty(name = "orderNum", value = "排序", dataType = "Integer")
    private Integer orderNum;

    /**
     * 父id
     */
    @ApiModelProperty(name = "parentId", value = "父id", dataType = "Integer")
    private Integer parentId;

    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "状态", dataType = "Integer")
    private Integer status;

    /**
     * 类型
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;
}
