package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客详细信息dto
 * @author lee
 * @create 2021-09-17 14:33
 **/
@Data
public class BlogInfoDTO {

    /**
     * id
     */
    private Long blogId;

    /**
     * 博客封面
     */
    private String firstPicture;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 博客类型
     */
    private Integer blogType;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

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

    /**
     * 上一篇博客
     */
    private BlogPaginationDTO lastBlog;

    /**
     * 下一篇博客
     */
    private BlogPaginationDTO nextBlog;

    /**
     * 推荐博客列表
     */
    private List<BlogRecommendDTO> recommendBlogList;

    /**
     * 最新文章列表
     */
    private List<BlogRecommendDTO> newBlogList;
}
