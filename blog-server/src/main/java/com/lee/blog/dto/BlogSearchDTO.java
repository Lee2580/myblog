package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author lee
 * @create 2021-09-18 18:58
 **/
@Data
@Builder
public class BlogSearchDTO {

    /**
     * 博客id
     */
    @Id
    private Long blogId;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 博客状态
     */
    private Integer status;
}
