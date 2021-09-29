package com.lee.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee
 * @create 2021-09-16 15:11
 **/
@Data
public class UserOnlineDTO {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
}
