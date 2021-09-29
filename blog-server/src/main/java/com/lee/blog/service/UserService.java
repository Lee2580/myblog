package com.lee.blog.service;

import com.lee.blog.dto.*;
import com.lee.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);

    void updateUserRole(UserRoleVo userRoleVo);

    void updateUserStatus(StatusVo userStatusVo);

    PageResult<UserListDTO> getUserListDTO(ConditionVo condition);

    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVo conditionVo);

    void removeOnlineUser(Long userId);

    void updateAdminPassword(PasswordVo passwordVo);

    void updateUserInfo(UserInfoVo userInfoVo);

    List<UserAreaDTO> listUserAreas(Integer type);

    UserDTO weiboLogin(WeiboLoginVo weiBoLoginVo);

    void sendCode(String username);

    void saveUserEmail(EmailVo emailVo);

    void updatePassword(UserVo user);

    void register(UserVo user);

    String updateUserAvatar(MultipartFile file);
}
