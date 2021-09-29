package com.lee.blog.dto;

import com.lee.blog.vo.PageVo;
import com.lee.blog.vo.WebsiteConfigVo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 博客首页信息
 * @author lee
 * @create 2021-09-18 20:09
 **/
@Data
@Builder
public class BlogHomeInfoDTO {
    /**
     * 博客数量
     */
    private Integer blogCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVo websiteConfig;

    /**
     * 页面列表
     */
    private List<PageVo> pageList;
}
