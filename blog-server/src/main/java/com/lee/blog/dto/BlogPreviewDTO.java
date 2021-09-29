package com.lee.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 预览博客信息
 * @author lee
 * @create 2021-09-20 11:03
 **/
@Data
public class BlogPreviewDTO {

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 博客首图
     */
    private String firstPicture;

    /**
     * 标题
     */
    private String title;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 博客分类id
     */
    private Long categoryId;

    /**
     * 博客分类名
     */
    private String categoryName;

    /**
     * 博客标签
     */
    private List<TagDTO> tagDTOList;
}
