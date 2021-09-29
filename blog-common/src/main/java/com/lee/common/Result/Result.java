package com.lee.common.Result;

import lombok.Data;

import static com.lee.common.enums.StatusCodeEnum.FAIL;
import static com.lee.common.enums.StatusCodeEnum.SUCCESS;

/**
 * 返回类
 * @author: lee
 * @create: 2021-09-07 12:35
 **/
@Data
public class Result {
    /**
     * 返回状态，状态码，信息，数据
     */
    private boolean status;
    private Integer code;
    private String message;
    private Object data;

    /**
     * 操作成功
     */
    public static Result success(){
        return success(true,SUCCESS.getCode(),SUCCESS.getMessage(),null);
    }

    public static Result success(Object data){
        return success(true,SUCCESS.getCode(),SUCCESS.getMessage(),data);
    }

    public static Result success(Boolean status,Integer code,String message,Object data){
        Result result = new Result();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


    /**
     * 操作失败
     */
    public static Result fail(){
        return fail(false, FAIL.getCode(), FAIL.getMessage(), null);
    }

    public static Result fail(Object data){
        return fail(false, FAIL.getCode(), FAIL.getMessage(), data);

    }

    public static Result fail(String message){
        return fail(false, FAIL.getCode(), message,null);
    }

    public static Result fail(Integer code,String message){
        return fail(false,code,message,null);
    }

    public static Result fail(String message,Object data){
        return fail(false, FAIL.getCode(), message,data);
    }

    public static Result fail(Boolean status,Integer code,String message,Object data){
        Result result = new Result();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

}
