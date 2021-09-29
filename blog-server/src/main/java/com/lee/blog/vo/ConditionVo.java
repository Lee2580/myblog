package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lee
 * @create 2021-09-15 10:54
 **/
@Data
public class ConditionVo {

    /**
     * 页码
     */
    @ApiModelProperty(name = "current", value = "页码", dataType = "Long")
    private Long current;

    /**
     * 条数
     */
    @ApiModelProperty(name = "size", value = "条数", dataType = "Long")
    private Long size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;

    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Long")
    private Long categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Long")
    private Long tagId;

    /**
     * 类型    原创/转载
     */
    @ApiModelProperty(name = "blogType", value = "类型", dataType = "Integer")
    private Integer blogType;

    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "状态", dataType = "Integer")
    private Integer status;

    /**
     * 是否删除
     */
    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @ApiModelProperty(name = "isReview", value = "审核状态", dataType = "Integer")
    private Integer isReview;


    /**
     * 评论类型   1 博客    2 友链
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;

    /**
     * 登录类型
     */
    @ApiModelProperty(name = "type", value = "登录类型", dataType = "Integer")
    private Integer loginType;
}
