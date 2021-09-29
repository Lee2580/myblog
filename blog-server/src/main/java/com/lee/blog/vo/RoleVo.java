package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色vo
 * @author lee
 * @create 2021-09-14 18:43
 **/
@Data
public class RoleVo {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", value = "角色id", dataType = "Long")
    private Long roleId;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(name = "name", value = "角色名", required = true, dataType = "String")
    private String name;

    /**
     * 角色描述
     */
    @NotBlank(message = "角色描述不能为空")
    @ApiModelProperty(name = "label", value = "角色描述", required = true, dataType = "String")
    private String label;

    /**
     * 资源列表
     */
    @ApiModelProperty(name = "resourceIdList", value = "资源列表", required = true, dataType = "List<Long>")
    private List<Long> resourceIdList;

    /**
     * 菜单列表
     */
    @ApiModelProperty(name = "menuIdList", value = "菜单列表", required = true, dataType = "List<Long>")
    private List<Long> menuIdList;
}
