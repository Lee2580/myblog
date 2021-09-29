package com.lee.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 标签vo
 * @author lee
 * @create 2021-09-15 21:35
 **/
@Data
@ApiModel(description = "标签vo")
public class TagVo {

    /**
     * id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Long")
    private Long tagId;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    @ApiModelProperty(name = "tagName", value = "标签名", required = true, dataType = "String")
    private String tagName;
}
