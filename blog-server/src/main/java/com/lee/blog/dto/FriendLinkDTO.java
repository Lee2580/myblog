package com.lee.blog.dto;

import lombok.Data;

/**
 * 展示友链dto
 * @author lee
 * @create 2021-09-20 11:50
 **/
@Data
public class FriendLinkDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 链接介绍
     */
    private String linkIntro;

}
