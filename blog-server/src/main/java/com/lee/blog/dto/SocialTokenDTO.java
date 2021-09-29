package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 社交登录token
 * @author lee
 * @create 2021-09-18 14:34
 **/
@Data
@Builder
public class SocialTokenDTO {

    /**
     * 开放id
     */
    private String openId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 登录类型
     */
    private Integer loginType;
}
