package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 首页博客dto
 * @author lee
 * @create 2021-09-18 21:14
 **/
@Data
public class HomeBlogDTO {

    /**
     * id
     */
    private Long blogId;

    /**
     * 博客首图
     */
    private String firstPicture;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 推荐状态
     */
    private Integer recommend;

    /**
     * 文章类型
     */
    private Integer blogType;

    /**
     * 博客分类id
     */
    private Long categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;
}
