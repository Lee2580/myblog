package com.lee.blog.dto;

import lombok.Data;

/**
 * @author lee
 * @create 2021-09-15 15:28
 **/
@Data
public class CategoryDTO {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类下的博客数量
     */
    private Integer blogCount;
}
