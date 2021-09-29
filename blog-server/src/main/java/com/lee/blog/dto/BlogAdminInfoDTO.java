package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 博客后台信息dto
 * @author lee
 * @create 2021-09-17 10:46
 **/
@Data
@Builder
public class BlogAdminInfoDTO {

    /**
     * 访问量
     */
    private Integer viewsCount;

    /**
     * 留言量
     */
    private Integer messageCount;

    /**
     * 用户量
     */
    private Integer userCount;

    /**
     * 博客量
     */
    private Integer blogCount;

    /**
     * 分类统计
     */
    private List<CategoryDTO> categoryDTOList;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;

    /**
     * 博客统计列表
     */
    private List<BlogStatisticsDTO> blogStatisticsList;

    /**
     * 一周用户量集合
     */
    private List<ViewsDTO> viewsDTOList;

    /**
     * 博客浏览量排行
     */
    private List<BlogRankDTO> blogRankDTOList;
}
