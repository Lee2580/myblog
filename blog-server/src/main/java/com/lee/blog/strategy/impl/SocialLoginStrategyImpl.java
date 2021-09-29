package com.lee.blog.strategy.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lee.blog.dto.SocialTokenDTO;
import com.lee.blog.dto.SocialUserDTO;
import com.lee.blog.dto.UserDTO;
import com.lee.blog.dto.UserDetailDTO;
import com.lee.blog.entity.User;
import com.lee.blog.entity.UserRole;
import com.lee.blog.mapper.UserMapper;
import com.lee.blog.mapper.UserRoleMapper;
import com.lee.blog.security.UserDetailServiceImpl;
import com.lee.blog.strategy.SocialLoginStrategy;
import com.lee.blog.util.IpUtil;
import com.lee.common.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 第三方登录抽象模板
 * @author lee
 * @create 2021-09-18 14:33
 **/
@Service
public abstract class SocialLoginStrategyImpl implements SocialLoginStrategy {

    @Resource
    HttpServletRequest request;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 第三方登录
     * @param data
     * @return
     */
    @Override
    public UserDTO login(String data) {

        // 创建登录信息
        UserDetailDTO userDetailDTO;
        // 获取第三方token信息
        SocialTokenDTO socialToken = getSocialToken(data);
        // 获取用户ip信息
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        // 判断是否注册
        User user = getUser(socialToken);
        if (Objects.nonNull(user)) {
            // 该用户不为空，返回数据库信息
            userDetailDTO = getUserDetail(user, ipAddress, ipSource);
        } else {
            // 获取第三方用户信息
            SocialUserDTO socialUserInfo = getSocialUserInfo(socialToken);
            // 保存用户账号信息
            User userInfo = User.builder()
                    .username(socialToken.getOpenId())
                    .password(socialToken.getAccessToken())
                    .loginType(socialToken.getLoginType())
                    .nickname(socialUserInfo.getNickname())
                    .avatar(socialUserInfo.getAvatar())
                    .ipAddress(ipAddress)
                    .ipSource(ipSource)
                    .build();
            userMapper.insert(userInfo);
            // 绑定角色
            UserRole userRole = UserRole.builder()
                    .userId(userInfo.getUserId())
                    .roleId(RoleEnum.USER.getRoleId())
                    .build();
            userRoleMapper.insert(userRole);
            // 封装登录信息
            userDetailDTO = userDetailsService.convertUserDetail(userInfo, request);
        }
        // 将登录信息放入springSecurity管理
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetailDTO, null, userDetailDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // 返回用户信息
        return BeanUtil.copyProperties(userDetailDTO, UserDTO.class);
    }

    /**
     * 获取第三方用户信息
     * @param socialToken
     * @return
     */
    protected abstract SocialUserDTO getSocialUserInfo(SocialTokenDTO socialToken);

    /**
     * 获取用户信息
     * @param user
     * @param ipAddress
     * @param ipSource
     * @return
     */
    private UserDetailDTO getUserDetail(User user, String ipAddress, String ipSource) {

        // 更新登录信息
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getLastLogin, LocalDateTime.now())
                .set(User::getIpAddress, ipAddress)
                .set(User::getIpSource, ipSource)
                .eq(User::getUserId, user.getUserId()));
        // 封装信息
        return userDetailsService.convertUserDetail(user, request);
    }

    /**
     * 获取用户账号信息
     * @param socialToken
     * @return
     */
    private User getUser(SocialTokenDTO socialToken) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, socialToken.getOpenId())
                .eq(User::getLoginType, socialToken.getLoginType()));
        return user;
    }

    /**
     * 获取第三方token信息
     * @param data
     * @return
     */
    public abstract SocialTokenDTO getSocialToken(String data);


}
