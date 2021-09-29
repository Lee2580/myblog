package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 友链状态vo
 * @author lee
 * @create 2021-09-16 19:30
 **/
@Data
public class FriendLinkStatusVo {

    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "友链id", dataType = "Long")
    private Long id;

    /**
     * 友链展示状态
     */
    @NotNull(message = "展示状态不能为空")
    @ApiModelProperty(name = "linkStatus", value = "展示状态", dataType = "Integer", required = true)
    private Integer linkStatus;
}
