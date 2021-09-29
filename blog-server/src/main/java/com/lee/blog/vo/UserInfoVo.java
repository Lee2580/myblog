package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户信息vo
 * @author lee
 * @create 2021-09-16 21:20
 **/
@Data
public class UserInfoVo {

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    /**
     * 用户简介
     */
    @ApiModelProperty(name = "intro", value = "介绍", dataType = "String")
    private String intro;

    /**
     * 个人网站
     */
    @ApiModelProperty(name = "webSite", value = "个人网站", dataType = "String")
    private String webSite;
}
