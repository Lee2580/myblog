package com.lee.blog.controller;

import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.UserDTO;
import com.lee.blog.dto.UserListDTO;
import com.lee.blog.dto.UserOnlineDTO;
import com.lee.blog.service.UserService;
import com.lee.blog.vo.*;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.lee.common.comstant.OptTypeConst.REMOVE;
import static com.lee.common.comstant.OptTypeConst.UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "用户管理模块")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /************************************
     *              用户列表             *
     * **********************************
     */

    /**
     * 修改用户角色
     * @param userRoleVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result updateUserRole(@Valid @RequestBody UserRoleVo userRoleVo) {
        userService.updateUserRole(userRoleVo);
        return Result.success();
    }

    /**
     * 修改用户状态  正常/禁用
     * @param userStatusVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户状态")
    @PutMapping("/admin/users/status")
    public Result updateUserStatus(@Valid @RequestBody StatusVo userStatusVo) {
        userService.updateUserStatus(userStatusVo);
        return Result.success();
    }

    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/admin/listUsers")
    public Result listUsers(ConditionVo condition) {
        PageResult<UserListDTO> list = userService.getUserListDTO(condition);
        return Result.success(list);
    }

    /************************************
     *         在线用户 session操作       *
     * **********************************
     */

    /**
     * session查看在线用户
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result listOnlineUsers( ConditionVo conditionVo) {
        PageResult<UserOnlineDTO> list = userService.listOnlineUsers(conditionVo);
        return Result.success(list);
    }

    /**
     * 下线用户
     * @param userId
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public Result removeOnlineUser(@PathVariable("userInfoId") Long userId) {
        userService.removeOnlineUser(userId);
        return Result.success();
    }

    /************************************
     *         admin修改信息、密码        *
     * **********************************
     */

    /**
     * admin修改密码
     * @param passwordVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "admin修改密码")
    @PutMapping("/admin/users/password")
    public Result updateAdminPassword(@Valid @RequestBody PasswordVo passwordVo) {
        userService.updateAdminPassword(passwordVo);
        return Result.success();
    }

    /**
     * 更新用户信息
     * @param userInfoVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新用户信息")
    @PutMapping("/users/info")
    public Result updateUserInfo(@Valid @RequestBody UserInfoVo userInfoVo) {
        userService.updateUserInfo(userInfoVo);
        return Result.success();
    }

    /**
     * 更新用户头像
     * @param file
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新用户头像")
    @PostMapping("/users/avatar")
    public Result updateUserAvatar(MultipartFile file) {
        String avatar = userService.updateUserAvatar(file);
        return Result.success(avatar);
    }


    /************************************
     *              博客前台             *
     * **********************************
     */

    /**
     * 微博登录
     * @param weiBoLoginVo
     * @return
     */
    @ApiOperation(value = "微博登录")
    @PostMapping("/users/oauth/weibo")
    public Result weiboLogin(@Valid @RequestBody WeiboLoginVo weiBoLoginVo) {
        UserDTO userDTO= userService.weiboLogin(weiBoLoginVo);
        return Result.success(userDTO);
    }

    /**
     * 发送邮箱验证码
     * @param username
     * @return
     */
    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/users/code")
    public Result sendCode(String username) {
        userService.sendCode(username);
        return Result.success();
    }

    /**
     * 绑定用户邮箱
     * @param emailVo
     * @return
     */
    @ApiOperation(value = "绑定用户邮箱")
    @PostMapping("/users/email")
    public Result saveUserEmail(@Valid @RequestBody EmailVo emailVo) {
        userService.saveUserEmail(emailVo);
        return Result.success();
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改密码")
    @PutMapping("/users/password")
    public Result updatePassword(@Valid @RequestBody UserVo user) {
        userService.updatePassword(user);
        return Result.success();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserVo user) {
        userService.register(user);
        return Result.success();
    }


}
