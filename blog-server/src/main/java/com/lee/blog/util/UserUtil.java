package com.lee.blog.util;

import com.lee.blog.dto.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author lee
 * @create 2021-09-15 19:21
 **/
@Slf4j
@Component
public class UserUtil {

    /**
     * 获取当前登录用户
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //log.info("getLoginUser ===>"+principal);
        return (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
