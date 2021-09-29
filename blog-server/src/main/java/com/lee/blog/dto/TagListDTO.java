package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee
 * @create 2021-09-15 20:33
 **/
@Data
public class TagListDTO extends TagDTO{

    /**
     * 博客数量
     */
    private Integer blogCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
