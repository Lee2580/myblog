package com.lee.blog.service;

import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.RoleDTO;
import com.lee.blog.dto.UserRoleDTO;
import com.lee.blog.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.RoleVo;
import com.lee.blog.vo.StatusVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface RoleService extends IService<Role> {

    PageResult<RoleDTO> listRoles(ConditionVo conditionVo);

    void deleteRoles(List<Integer> roleIdList);

    void saveOrUpdateRole(RoleVo roleVo);

    List<UserRoleDTO> listUserRoles();

    void updateRoleStatus(StatusVo roleStatusVo);
}
