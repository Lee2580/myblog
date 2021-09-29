package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推荐博客dto
 * @author lee
 * @create 2021-09-17 12:53
 **/
@Data
public class BlogRecommendDTO {

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 博客封面首图
     */
    private String firstPicture;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
