package com.lee.blog.dto;

import lombok.Data;

/**
 * 微博用户信息dto
 * @author lee
 * @create 2021-09-18 15:18
 **/
@Data
public class WeiboUserDTO {

    /**
     * 昵称
     */
    private String screen_name;

    /**
     * 头像
     */
    private String avatar_hd;

}
