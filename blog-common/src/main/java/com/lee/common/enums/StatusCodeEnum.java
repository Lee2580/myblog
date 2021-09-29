package com.lee.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lee
 * @create: 2021-09-07 13:48
 **/
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS(200,"操作成功"),
    FAIL(400,"操作失败"),
    FREQUENCY_OVER_HIGH(402,"操作频率过高"),
    AUTHORIZED(403, "操作权限不足"),
    SYSTEM_ERROR(500, "系统异常"),
    VALID_ERROR(520, "参数校验错误"),
    USERNAME_EXIST(52001, "用户名已存在"),
    USERNAME_NOT_EXIST(52002, "用户名不存在"),
    PASSWORD_ERROR(52003,"账户或密码错误"),
    USER_DISABLED(52004,"该账户已被禁用"),
    WEIBO_LOGIN_ERROR(53002, "微博登录错误");

    private final Integer code;

    private final String message;
}
