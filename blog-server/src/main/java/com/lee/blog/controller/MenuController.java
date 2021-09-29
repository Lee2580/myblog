package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.MenuDTO;
import com.lee.blog.dto.UserMenuDTO;
import com.lee.blog.service.MenuService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.MenuVo;
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
 *  菜单控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "菜单管理模块")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 查询当前用户菜单
     * @return
     */
    @ApiOperation(value = "查询当前用户菜单")
    @GetMapping("/admin/user/menus")
    public Result MenuListByUser(){
        List<UserMenuDTO> menuList= menuService.MenuListByUser();
        return Result.success(menuList);
    }

    /**
     * 根据条件查询菜单列表
     * @Requestbody 需要使用post提交，不能使用get提交
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "根据条件查询菜单列表")
    @GetMapping("/admin/menus")
    public Result listMenus( ConditionVo conditionVo) {
        List<MenuDTO> menuDTOList= menuService.listMenus(conditionVo);
        return Result.success(menuDTOList);
    }

    /**
     * 新增或修改菜单
     * @param menuVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/admin/saveOrUpdateMenu")
    public Result saveOrUpdateMenu(@Valid @RequestBody MenuVo menuVo) {
        menuService.saveOrUpdateMenu(menuVo);
        return Result.success();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/admin/menus/{menuId}")
    public Result deleteMenu(@PathVariable("menuId") Long menuId){
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 修改菜单状态  正常/禁用
     * @param menuStatusVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改菜单状态")
    @PutMapping("/admin/menus/status")
    public Result updateMenuStatus(@Valid @RequestBody StatusVo menuStatusVo) {
        menuService.updateMenuStatus(menuStatusVo);
        return Result.success();
    }
}
