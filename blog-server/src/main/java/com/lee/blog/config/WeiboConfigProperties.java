package com.lee.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lee
 * @create 2021-09-18 15:07
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "weibo")
public class WeiboConfigProperties {

    /**
     * 微博appId
     */
    private String appId;

    /**
     * 微博appSecret
     */
    private String appSecret;

    /**
     * 微博登录类型
     */
    private String grantType;

    /**
     * 微博回调域名
     */
    private String redirectUrl;

    /**
     * 微博访问令牌地址
     */
    private String accessTokenUrl;

    /**
     * 微博用户信息地址
     */
    private String userInfoUrl;
}
