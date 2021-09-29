package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关于我信息
 * @author lee
 * @create 2021-09-22 14:30
 **/
@Data
public class AboutVo {

    /**
     * 关于我内容
     */
    @ApiModelProperty(name = "aboutContent", value = "关于我内容", required = true, dataType = "String")
    private String aboutContent;
}
