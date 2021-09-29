package com.lee.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客浏览量排行
 * @author lee
 * @create 2021-09-17 11:09
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogRankDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Integer viewsCount;
}
