package com.lee.blog.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author lee
 * @create 2021-09-21 11:03
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * 操作类型
     * @return
     */
    String optType() default "";
}
