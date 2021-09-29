package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微博登录
 * @author lee
 * @create 2021-09-18 14:24
 **/
@Data
public class WeiboLoginVo {

    /**
     * code
     */
    @NotBlank(message = "code不能为空")
    @ApiModelProperty(name = "openId", value = "微博 openId", required = true, dataType = "String")
    private String code;
}
