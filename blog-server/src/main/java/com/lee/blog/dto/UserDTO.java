package com.lee.blog.dto;

import com.lee.blog.entity.User;
import lombok.Data;

import java.util.Set;

/**
 * 用户登录信息dto
 * @author lee
 * @create 2021-09-17 15:17
 **/
@Data
public class UserDTO extends User {

    /**
     * 点赞博客集合
     */
    private Set<Object> blogLikeSet;

    /**
     * 点赞评论集合
     */
    private Set<Object> commentLikeSet;

}
