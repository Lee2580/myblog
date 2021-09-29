package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lee
 * @create 2021-09-14 21:43
 **/
@Data
public class ResourceVo {

    /**
     * 资源id
     */
    @ApiModelProperty(name = "resourceId", value = "资源id", required = true, dataType = "Long")
    private Long resourceId;

    /**
     * 资源名
     */
    @NotBlank(message = "资源名不能为空")
    @ApiModelProperty(name = "resourceName", value = "资源名", required = true, dataType = "String")
    private String resourceName;

    /**
     * 路径
     */
    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String requestMethod;

    /**
     * 父资源id
     */
    @ApiModelProperty(name = "parentId", value = "父资源id", required = true, dataType = "Integer")
    private Integer parentId;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty(name = "isAnonymous", value = "是否匿名访问", required = true, dataType = "Integer")
    private Integer anonymous;
}
