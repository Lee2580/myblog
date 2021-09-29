package com.lee.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lee.blog.dto.RoleDTO;
import com.lee.blog.dto.UserRoleDTO;
import com.lee.blog.entity.*;
import com.lee.blog.mapper.RoleMapper;
import com.lee.blog.mapper.UserRoleMapper;
import com.lee.blog.security.FilterInvocationSecurityMetadataSourceImpl;
import com.lee.blog.service.RoleMenuService;
import com.lee.blog.service.RoleResourceService;
import com.lee.blog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.RoleVo;
import com.lee.blog.vo.StatusVo;
import com.lee.common.comstant.Const;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleResourceService roleResourceService;

    @Autowired
    FilterInvocationSecurityMetadataSourceImpl FilterInvocationSecurityMetadataSourceImpl;

    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 查询角色列表
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<RoleDTO> listRoles(ConditionVo conditionVo) {
        // 查询角色列表
        List<RoleDTO> roleDTOList = roleMapper.listRoles(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVo);
        //log.info("角色列表 ===>"+roleDTOList);
        // 查询总量
        Integer count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Role::getName, conditionVo.getKeywords()));
        //log.info("查询总量 ===>"+count);
        return new PageResult<>(roleDTOList, count);
    }

    /**
     * 删除角色
     * @param roleIdList
     */
    @Transactional
    @Override
    public void deleteRoles(List<Integer> roleIdList) {

        // 判断角色下是否有用户
        Integer count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BizException("该角色下存在用户，无法删除");
        }
        roleMapper.deleteBatchIds(roleIdList);
    }

    /**
     * 保存更新角色信息
     * @param roleVo
     */
    @Transactional
    @Override
    public void saveOrUpdateRole(RoleVo roleVo) {
        // 判断角色名重复
        Role nowRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getRoleId)
                .eq(Role::getName, roleVo.getName()));
        if (Objects.nonNull(nowRole) && !nowRole.getRoleId().equals(roleVo.getRoleId())) {
            throw new BizException("角色名已存在，请重新填写");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .roleId(roleVo.getRoleId())
                .name(roleVo.getName())
                .label(roleVo.getLabel())
                .status(Const.FALSE)
                .build();
        this.saveOrUpdate(role);

        // 更新资源列表
        if (CollectionUtils.isNotEmpty(roleVo.getResourceIdList())) {
            //删除旧的列表
            if (Objects.nonNull(roleVo.getRoleId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVo.getRoleId()));
            }
            //添加新的列表
            List<RoleResource> roleResourceList = roleVo.getResourceIdList().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getRoleId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            FilterInvocationSecurityMetadataSourceImpl.clearDataSource();
        }

        // 更新菜单列表
        if (CollectionUtils.isNotEmpty(roleVo.getMenuIdList())) {
            if (Objects.nonNull(roleVo.getRoleId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, roleVo.getRoleId()));
            }
            List<RoleMenu> roleMenuList = roleVo.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getRoleId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    /**
     * 查询用户角色选项
     * @return
     */
    @Override
    public List<UserRoleDTO> listUserRoles() {

        // 查询角色列表
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getRoleId, Role::getName));
        return BeanCopyUtil.copyList(roleList, UserRoleDTO.class);
    }

    /**
     * 修改角色状态
     * @param roleStatusVo
     */
    @Override
    public void updateRoleStatus(StatusVo roleStatusVo) {

        Role role = Role.builder()
                .roleId(roleStatusVo.getId())
                .status(roleStatusVo.getStatus())
                .build();
        roleMapper.updateById(role);
    }


}
