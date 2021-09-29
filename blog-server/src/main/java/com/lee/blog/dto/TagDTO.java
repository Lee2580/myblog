package com.lee.blog.dto;

import lombok.Data;

/**
 * @author lee
 * @create 2021-09-15 10:37
 **/
@Data
public class TagDTO {

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 标签名
     */
    private String tagName;
}
