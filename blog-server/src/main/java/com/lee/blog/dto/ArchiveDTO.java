package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 归档博客dto
 * @author lee
 * @create 2021-09-20 10:17
 **/
@Data
public class ArchiveDTO {

    /**
     * id
     */
    private Long blogId;

    /**
     * 标题
     */
    private String title;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

}
