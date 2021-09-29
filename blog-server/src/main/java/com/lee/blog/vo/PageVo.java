package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 页面信息vo
 * @author lee
 * @create 2021-09-16 17:34
 **/
@Data
public class PageVo {

    /**
     * 页面id
     */
    //@NotNull(message = "页面id不能为空")
    @ApiModelProperty(name = "id", value = "页面id", required = true, dataType = "Long")
    private Long id;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名称不能为空")
    @ApiModelProperty(name = "pageName", value = "页面名称", required = true, dataType = "String")
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空")
    @ApiModelProperty(name = "pageLabel", value = "页面标签", required = true, dataType = "String")
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空")
    @ApiModelProperty(name = "pageCover", value = "页面封面", required = true, dataType = "String")
    private String pageCover;
}
