package com.lee.blog.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lee
 * @create 2021-09-15 10:09
 **/
@Data
public class BlogRecommendVo {

    /**
     * id
     */
    @NotNull(message = "blogId不能为空")
    private Long blogId;

    /**
     * 推荐状态 0否 1推荐
     */
    @NotNull(message = "推荐状态不能为空")
    private Integer recommend;
}
