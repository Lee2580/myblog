package com.lee.blog.dto;

import lombok.Data;

/**
 * 博客统计dto
 * @author lee
 * @create 2021-09-17 10:54
 **/
@Data
public class BlogStatisticsDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Integer count;
}
