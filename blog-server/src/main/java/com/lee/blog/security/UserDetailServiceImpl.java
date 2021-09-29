package com.lee.blog.security;

import com.lee.blog.dto.UserDetailDTO;
import com.lee.blog.entity.User;
import com.lee.blog.mapper.RoleMapper;
import com.lee.blog.service.RedisService;
import com.lee.blog.service.UserService;
import com.lee.blog.util.IpUtil;
import com.lee.common.exception.BizException;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.lee.common.comstant.RedisConst.BLOG_USER_LIKE;
import static com.lee.common.comstant.RedisConst.COMMENT_USER_LIKE;

/**
 * 用户信息服务
 *      security从数据库中获取用户信息
 * @author lee
 * @create 2021-09-10 11:12
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)){
            throw new BizException("用户名不能为空");
        }

        // 查询账号是否存在
        User user = userService.getByUsername(username);;
        if (Objects.isNull(user)) {
            throw new BizException("用户名不存在!");
        }
        // 封装登录信息
        return convertUserDetail(user, httpServletRequest);
    }

    /**
     * 封装用户登录信息
     * @param user
     * @param request
     * @return
     */
    public UserDetailDTO convertUserDetail(User user, HttpServletRequest request) {

        // 查询账号角色
        List<String> roleList = roleMapper.listRolesByUserId(user.getUserId());
        // 查询账号点赞信息
        Set<Object> blogLikeSet = redisService.sMembers(BLOG_USER_LIKE + user.getUserId());
        Set<Object> commentLikeSet = redisService.sMembers(COMMENT_USER_LIKE + user.getUserId());
        // 获取ip信息
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .userId(user.getUserId())
                .loginType(user.getLoginType())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roleList(roleList)
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .intro(user.getIntro())
                .webSite(user.getWebSite())
                .blogLikeSet(blogLikeSet)
                .commentLikeSet(commentLikeSet)
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .status(user.getStatus())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLogin(LocalDateTime.now(ZoneId.of("Asia/Shanghai")))
                .build();
    }
}
