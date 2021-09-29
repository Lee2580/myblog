package com.lee.blog.dto;

import lombok.Data;

/**
 * @author lee
 * @create 2021-09-20 12:16
 **/
@Data
public class MessageDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 弹幕速度
     */
    private Integer time;
}
