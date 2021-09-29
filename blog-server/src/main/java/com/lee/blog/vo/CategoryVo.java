package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 分类vo
 * @author lee
 * @create 2021-09-15 22:31
 **/
@Data
public class CategoryVo {
    /**
     * id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Long")
    private Long categoryId;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @ApiModelProperty(name = "categoryName", value = "分类名", required = true, dataType = "String")
    private String categoryName;
}
