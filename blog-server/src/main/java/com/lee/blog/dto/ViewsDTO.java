package com.lee.blog.dto;

import lombok.Data;

/**
 * 访问量
 * @author lee
 * @create 2021-09-17 11:08
 **/
@Data
public class ViewsDTO {

    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;
}
