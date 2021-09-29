package com.lee.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @TableId(value = "blog_id", type = IdType.AUTO)
    private Long blogId;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 博客类型 1原创 2转载 3翻译
     */
    private Integer blogType;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;

    /**
     * 点赞数
     */
    private Integer thumbs;

    /**
     * 推荐状态 0否 1推荐
     */
    private Integer recommend;

    /**
     * 发布状态 1公开 2私密 3评论可见
     */
    private Integer status;

    /**
     * 浏览次数
     */
    private Integer views;

}
