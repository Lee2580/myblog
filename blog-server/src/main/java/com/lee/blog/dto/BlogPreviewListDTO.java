package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 博客预览列表
 * @author lee
 * @create 2021-09-20 11:00
 **/
@Data
@Builder
public class BlogPreviewListDTO {

    /**
     * 博客列表
     */
    private List<BlogPreviewDTO> blogPreviewDTOList;

    /**
     * 条件名
     */
    private String name;
}
