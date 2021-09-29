package com.lee.blog.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误处理
 * @author lee
 * @create 2021-09-09 21:34
 **/
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
