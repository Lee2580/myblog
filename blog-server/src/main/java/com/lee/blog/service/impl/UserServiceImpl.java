package com.lee.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lee.blog.dto.*;
import com.lee.blog.entity.User;
import com.lee.blog.entity.UserRole;
import com.lee.blog.mapper.UserMapper;
import com.lee.blog.mapper.UserRoleMapper;
import com.lee.blog.service.RedisService;
import com.lee.blog.service.UserRoleService;
import com.lee.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.strategy.SocialLoginStrategyContext;
import com.lee.blog.strategy.UploadStrategyContext;
import com.lee.blog.util.PageUtil;
import com.lee.blog.util.UserUtil;
import com.lee.blog.vo.*;
import com.lee.common.comstant.Const;
import com.lee.common.enums.FilePathEnum;
import com.lee.common.enums.LoginTypeEnum;
import com.lee.common.enums.RoleEnum;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.lee.common.comstant.MQConst.EMAIL_EXCHANGE;
import static com.lee.common.comstant.RedisConst.*;
import static com.lee.blog.util.CommonUtil.checkEmail;
import static com.lee.blog.util.CommonUtil.getRandomCode;
import static com.lee.blog.util.PageUtil.getLimitCurrent;
import static com.lee.blog.util.PageUtil.getSize;
import static com.lee.common.enums.UserAreaTypeEnum.getUserAreaType;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    RedisService redisService;

    @Autowired
    SocialLoginStrategyContext socialLoginStrategyContext;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UploadStrategyContext uploadStrategyContext;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username",username));
    }

    /**
     * 修改用户角色
     * @param userRoleVo
     */
    @Override
    public void updateUserRole(UserRoleVo userRoleVo) {
        // 更新用户角色和昵称
        User user = User.builder()
                .userId(userRoleVo.getUserId())
                .nickname(userRoleVo.getNickname())
                .build();
        userMapper.updateById(user);
        // 删除用户角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVo.getUserId()));
        //重新添加用户角色
        List<UserRole> userRoleList = userRoleVo.getRoleIdList().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(roleId)
                        .userId(userRoleVo.getUserId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    /**
     * 修改用户状态
     * @param userStatusVo
     */
    @Override
    public void updateUserStatus(StatusVo userStatusVo) {
        //log.info("userStatusVo ===>"+userStatusVo);
        // 更新用户状态
        User user = User.builder()
                .userId(userStatusVo.getId())
                .status(userStatusVo.getStatus())
                .build();
        userMapper.updateById(user);
    }

    /**
     * 获取用户列表
     * @param condition
     * @return
     */
    @Override
    public PageResult<UserListDTO> getUserListDTO(ConditionVo condition) {

        // 获取后台用户数量
        Integer count = userMapper.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 获取后台用户列表
        List<UserListDTO> userBackDTOList = userMapper.listUsers(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), condition);
        return new PageResult<>(userBackDTOList, count);
    }

    /**
     * session查询在线用户
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVo conditionVo) {

        // 获取security在线session
        List<UserOnlineDTO> userOnlineDTOList = sessionRegistry.getAllPrincipals().stream()
                .filter(item -> sessionRegistry.getAllSessions(item, false).size() > 0)
                .map(item -> JSON.parseObject(JSON.toJSONString(item), UserOnlineDTO.class))
                .sorted(Comparator.comparing(UserOnlineDTO::getLastLoginTime).reversed())
                .collect(Collectors.toList());
        // 执行分页
        int fromIndex = getLimitCurrent().intValue();
        int size = getSize().intValue();
        int toIndex = userOnlineDTOList.size() - fromIndex > size ? fromIndex + size : userOnlineDTOList.size();
        List<UserOnlineDTO> userOnlineList = userOnlineDTOList.subList(fromIndex, toIndex);
        return new PageResult<>(userOnlineList, userOnlineDTOList.size());
    }

    /**
     * session下线用户
     * @param userId
     */
    @Override
    public void removeOnlineUser(Long userId) {

        // 获取用户session
        List<Object> userInfoList = sessionRegistry.getAllPrincipals().stream().filter(item -> {
            UserDetailDTO userDetailDTO = (UserDetailDTO) item;
            return userDetailDTO.getUserId().equals(userId);
        }).collect(Collectors.toList());
        List<SessionInformation> allSessions = new ArrayList<>();
        userInfoList.forEach(item -> allSessions.addAll(sessionRegistry.getAllSessions(item, false)));
        // 注销session
        allSessions.forEach(SessionInformation::expireNow);
    }

    /**
     * admin修改密码
     * @param passwordVo
     */
    @Transactional
    @Override
    public void updateAdminPassword(PasswordVo passwordVo) {
        // 查询旧密码是否正确
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, UserUtil.getLoginUser().getUserId()));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVo.getOldPassword(), user.getPassword())) {
            User afterUser = User.builder()
                    .userId(UserUtil.getLoginUser().getUserId())
                    .password(BCrypt.hashpw(passwordVo.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userMapper.updateById(afterUser);
        } else {
            throw new BizException("旧密码不正确");
        }
    }

    /**
     * 修改用户信息
     * @param userInfoVo
     */
    @Transactional
    @Override
    public void updateUserInfo(UserInfoVo userInfoVo) {

        // 封装用户信息
        User user = User.builder()
                .userId(UserUtil.getLoginUser().getUserId())
                .nickname(userInfoVo.getNickname())
                .intro(userInfoVo.getIntro())
                .webSite(userInfoVo.getWebSite())
                .build();
        userMapper.updateById(user);
    }

    /**
     * 获取用户区域分布情况
     * @param type
     * @return
     */
    @Override
    public List<UserAreaDTO> listUserAreas(Integer type) {

        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(type))) {
            case USER:
                // 查询注册用户区域分布
                Object userArea = redisService.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                // 查询游客区域分布
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;
    }

    /**
     * 微博登录
     * @param weiBoLoginVo
     * @return
     */
    @Override
    public UserDTO weiboLogin(WeiboLoginVo weiBoLoginVo) {

        UserDTO userDTO = socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(weiBoLoginVo), LoginTypeEnum.WEIBO);

        return userDTO;
    }

    /**
     * 发送邮箱验证码
     * @param username
     */
    @Override
    public void sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new BizException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();

        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    /**
     * 绑定邮箱
     * @param emailVo
     */
    @Override
    public void saveUserEmail(EmailVo emailVo) {
        //判断验证码
        if (!emailVo.getCode().equals(redisService.get(USER_CODE_KEY + emailVo.getEmail()).toString())) {
            throw new BizException("验证码错误！");
        }
        User user = User.builder()
                .userId(UserUtil.getLoginUser().getUserId())
                .email(emailVo.getEmail())
                .build();
        userMapper.updateById(user);
    }

    /**
     * 修改密码
     * @param user
     */
    @Transactional
    @Override
    public void updatePassword(UserVo user) {

        // 校验账号是否合法
        if (!checkUser(user)) {
            throw new BizException("邮箱尚未注册！");
        }
        // 根据用户名修改密码
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(User::getUsername, user.getUsername()));
    }

    /**
     * 用户注册
     * @param user
     */
    @Transactional
    @Override
    public void register(UserVo user) {

        // 校验账号是否合法
        if (checkUser(user)) {
            throw new BizException("邮箱已被注册！");
        }
        // 新增用户信息
        User userInfo = User.builder()
                .email(user.getUsername())
                .nickname(Const.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(Const.DEFAULT_AVATAR)
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userMapper.insert(userInfo);

        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getUserId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
    }

    /**
     * 更新用户头像
     * @param file
     * @return
     */
    @Override
    public String updateUserAvatar(MultipartFile file) {

        // 头像上传
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        // 更新用户信息
        User user = User.builder()
                .userId(UserUtil.getLoginUser().getUserId())
                .avatar(avatar)
                .build();
        userMapper.updateById(user);
        return avatar;
    }

    /**
     * 检验用户数据
     * @param user
     * @return
     */
    private boolean checkUser(UserVo user) {

        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        //查询用户名是否存在
        User userInfo = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, user.getUsername()));

        return Objects.nonNull(userInfo);
    }
}
