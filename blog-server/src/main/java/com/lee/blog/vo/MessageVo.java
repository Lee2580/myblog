package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 留言信息vo
 * @author lee
 * @create 2021-09-20 12:10
 **/
@Data
public class MessageVo {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", required = true, dataType = "String")
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    @ApiModelProperty(name = "avatar", value = "头像", required = true, dataType = "String")
    private String avatar;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空")
    @ApiModelProperty(name = "messageContent", value = "留言内容", required = true, dataType = "String")
    private String messageContent;

    /**
     * 弹幕速度
     */
    @NotNull(message = "弹幕速度不能为空")
    @ApiModelProperty(name = "time", value = "弹幕速度", required = true, dataType = "Integer")
    private Integer time;
}
