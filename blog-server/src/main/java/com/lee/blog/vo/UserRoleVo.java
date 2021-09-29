package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户角色vo
 * @author lee
 * @create 2021-09-16 13:47
 **/
@Data
public class UserRoleVo {

    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "userId", value = "用户id", dataType = "Long")
    private Long userId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    /**
     * 用户角色
     */
    @NotNull(message = "用户角色不能为空")
    @ApiModelProperty(name = "roleIdList", value = "角色id集合", dataType = "List<Long>")
    private List<Long> roleIdList;
}
