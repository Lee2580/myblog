package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 社交账号信息
 * @author lee
 * @create 2021-09-18 14:47
 **/
@Data
@Builder
public class SocialUserDTO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;
}
