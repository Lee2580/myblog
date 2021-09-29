package com.lee.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lee
 * @create 2021-09-15 18:27
 **/
@AllArgsConstructor
@Getter
public enum BlogStatusEnum {

    /**
     * 公开
     */
    PUBLIC(1, "公开"),
    /**
     * 私密
     */
    SECRET(2, "私密"),
    /**
     * 草稿
     */
    DRAFT(3, "草稿");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;
}
