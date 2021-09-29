package com.lee.blog.dto;

import lombok.Data;

/**
 * 微博token信息
 * @author lee
 * @create 2021-09-18 15:03
 **/
@Data
public class WeiboTokenDTO {

    /**
     * 微博uid
     */
    private String uid;

    /**
     * 访问令牌
     */
    private String access_token;
}
