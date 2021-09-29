package com.lee.blog.security;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.lee.blog.dto.UserDTO;
import com.lee.blog.entity.User;
import com.lee.blog.mapper.UserMapper;
import com.lee.blog.util.UserUtil;
import com.lee.common.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 * @author lee
 * @create 2021-09-09 21:02
 **/
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserMapper userAuthDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        // 返回登录信息
        UserDTO userLoginDTO = BeanUtil.copyProperties(UserUtil.getLoginUser(), UserDTO.class);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(userLoginDTO)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息
     *      开启异步处理
     */
    @Async
    public void updateUserInfo() {
        User user = User.builder()
                .userId(UserUtil.getLoginUser().getUserId())
                .ipAddress(UserUtil.getLoginUser().getIpAddress())
                .ipSource(UserUtil.getLoginUser().getIpSource())
                .lastLogin(UserUtil.getLoginUser().getLastLogin())
                .build();
        userAuthDao.updateById(user);
    }
}
