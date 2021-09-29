package com.lee.blog.dto;

import com.lee.blog.entity.Blog;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客DTO，用于后台数据传输
 * @author lee
 * @create 2021-09-15 10:30
 **/
@Data
public class BlogDTO{

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
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;

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
     * 博客类型 1原创 2转载 3翻译
     */
    private Integer blogType;

    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;

    /**
     * 推荐状态 0否 1推荐
     */
    private Integer recommend;

    /**
     * 发布状态 1公开 2私密 3评论可见
     */
    private Integer status;

}
