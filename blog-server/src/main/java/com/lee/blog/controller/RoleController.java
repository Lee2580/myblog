package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.RoleDTO;
import com.lee.blog.dto.UserRoleDTO;
import com.lee.blog.service.MenuService;
import com.lee.blog.service.ResourceService;
import com.lee.blog.service.RoleService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.RoleVo;
import com.lee.blog.vo.StatusVo;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.*;

/**
 * <p>
 *  角色控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "角色管理模块")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    MenuService menuService;

    /**
     * 查询角色列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/admin/roles")
    public Result listRoles( ConditionVo conditionVo) {

        PageResult<RoleDTO> listRoles= roleService.listRoles(conditionVo);
        return Result.success(listRoles);
    }

    /**
     * 查询角色资源权限列表
     * @return
     */
    @ApiOperation(value = "查询角色资源权限列表")
    @GetMapping("/admin/role/resources")
    public Result getResourceListByRole() {

        List<LabelDTO> list = resourceService.getResourceListByRole();
        return Result.success(list);
    }

    /**
     * 查询角色菜单权限列表
     * @return
     */
    @ApiOperation(value = "查询角色菜单权限列表")
    @GetMapping("/admin/role/menus")
    public Result getMenuListByRole() {

        List<LabelDTO> list = menuService.getMenuListByRole();
        return Result.success(list);
    }

    /**
     * 删除角色
     * @param roleIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/deleteRoles")
    public Result deleteRoles(@RequestBody List<Integer> roleIdList) {

        roleService.deleteRoles(roleIdList);
        return Result.success();
    }

    /**
     * 保存更新角色信息
     * @param roleVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新角色信息")
    @PostMapping("/admin/saveOrUpdateRole")
    public Result saveOrUpdateRole(@RequestBody @Valid RoleVo roleVo) {

        roleService.saveOrUpdateRole(roleVo);
        return Result.success();
    }

    /**
     * 查询用户角色选项
     * @return
     */
    @ApiOperation(value = "查询用户角色选项")
    @GetMapping("/admin/users/role")
    public Result listUserRoles() {
        List<UserRoleDTO> list = roleService.listUserRoles();
        return Result.success(list);
    }

    /**
     * 修改角色状态  正常/禁用
     * @param roleStatusVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改角色状态")
    @PutMapping("/admin/roles/status")
    public Result updateUserStatus(@Valid @RequestBody StatusVo roleStatusVo) {
        roleService.updateRoleStatus(roleStatusVo);
        return Result.success();
    }

}
