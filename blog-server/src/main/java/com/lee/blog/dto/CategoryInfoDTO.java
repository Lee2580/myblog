package com.lee.blog.dto;

import lombok.Data;

/**
 * 分类及博客数量dto
 * @author lee
 * @create 2021-09-20 11:26
 **/
@Data
public class CategoryInfoDTO extends CategoryDTO{
    /**
     * 分类下的博客数量
     */
    private Integer blogCount;
}
