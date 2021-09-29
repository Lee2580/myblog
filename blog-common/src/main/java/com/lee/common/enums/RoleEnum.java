package com.lee.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 * @author lee
 * @create 2021-09-18 14:58
 **/
@AllArgsConstructor
@Getter
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(1L, "管理员", "admin"),
    /**
     * 普通用户
     */
    USER(2L, "用户", "user"),
    /**
     * 测试账号
     */
    TEST(3L, "测试", "test");

    /**
     * 角色id
     */
    private final Long roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;
}
