package com.lee.common.exception;

import com.lee.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.security.sasl.AuthenticationException;

import static com.lee.common.enums.StatusCodeEnum.FAIL;

/**
 * @author: lee
 * @create: 2021-09-07 13:46
 **/
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    private Integer code = FAIL.getCode();
    private final String message;

    public BizException(String message){
        this.message=message;
    }

    public BizException(StatusCodeEnum statusCodeEnum){
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getMessage();
    }
}
