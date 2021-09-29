package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author lee
 * @create 2021-09-15 16:30
 **/
@Data
public class BlogVo {

    /**
     * 博客id
     */
    @ApiModelProperty(name = "blogId", value = "博客id", dataType = "Long")
    private Long blogId;

    /**
     * 标题
     */
    @NotBlank(message = "博客标题不能为空")
    @ApiModelProperty(name = "title", value = "博客标题", required = true, dataType = "String")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "博客内容不能为空")
    @ApiModelProperty(name = "content", value = "博客内容", required = true, dataType = "String")
    private String content;

    /**
     * 首图
     */
    @ApiModelProperty(name = "firstPicture", value = "博客首图", dataType = "String")
    private String firstPicture;

    /**
     * 博客类型 1原创 2转载 3翻译
     */
    @ApiModelProperty(name = "blogType", value = "博客类型", dataType = "Integer")
    private Integer blogType;

    /**
     * 原文链接
     */
    @ApiModelProperty(name = "originalUrl", value = "原文链接", dataType = "String")
    private String originalUrl;

    /**
     * 推荐状态 0否 1推荐
     */
    @ApiModelProperty(name = "recommend", value = "推荐状态", dataType = "Integer")
    private Integer recommend;

    /**
     * 发布状态 1公开 2私密 3评论可见
     */
    @ApiModelProperty(name = "status", value = "博客状态", dataType = "String")
    private Integer status;

    /**
     * 博客分类
     */
    @ApiModelProperty(name = "categoryNameList", value = "博客分类", dataType = "String")
    private String categoryName;

    /**
     * 博客标签
     */
    @ApiModelProperty(name = "tagNameList", value = "博客标签", dataType = "List<String>")
    private List<String> tagNameList;

}
