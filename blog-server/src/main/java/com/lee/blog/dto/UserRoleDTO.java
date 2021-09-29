package com.lee.blog.dto;

import lombok.Data;

/**
 * @author lee
 * @create 2021-09-14 17:11
 **/
@Data
public class UserRoleDTO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;
}
