package com.lee.blog.strategy;

import com.lee.blog.dto.UserDTO;

/**
 * 第三方社交登录策略
 * @author lee
 * @create 2021-09-18 14:29
 **/
public interface SocialLoginStrategy {

    UserDTO login(String data);

}
