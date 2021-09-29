package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 友链vo
 * @author lee
 * @create 2021-09-16 18:07
 **/
@Data
public class FriendLinkVo {

    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "友链id", dataType = "Long")
    private Long id;

    /**
     * 友链名
     */
    @NotBlank(message = "链接名不能为空")
    @ApiModelProperty(name = "linkName", value = "友链名", dataType = "String", required = true)
    private String linkName;

    /**
     * 链接头像
     */
    @NotBlank(message = "链接头像不能为空")
    @ApiModelProperty(name = "linkAvatar", value = "友链头像", dataType = "String", required = true)
    private String linkAvatar;

    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(name = "linkAddress", value = "友链头像", dataType = "String", required = true)
    private String linkAddress;

    /**
     * 链接介绍
     */
    @NotBlank(message = "链接介绍不能为空")
    @ApiModelProperty(name = "linkIntro", value = "友链头像", dataType = "String", required = true)
    private String linkIntro;

    /**
     * 友链展示状态
     */
    @NotNull(message = "展示状态不能为空")
    @ApiModelProperty(name = "linkStatus", value = "展示状态", dataType = "Integer", required = true)
    private Integer linkStatus;
}
