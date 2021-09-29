package com.lee.blog.dto;

import lombok.Data;

/**
 * 博客上下篇
 * @author lee
 * @create 2021-09-17 13:05
 **/
@Data
public class BlogPaginationDTO {

    /**
     * id
     */
    private Long BlogId;

    /**
     * 博客首图
     */
    private String firstPicture;

    /**
     * 标题
     */
    private String title;
}
