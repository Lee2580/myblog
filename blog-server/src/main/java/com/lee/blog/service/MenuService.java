package com.lee.blog.service;

import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.MenuDTO;
import com.lee.blog.dto.UserMenuDTO;
import com.lee.blog.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.MenuVo;
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
public interface MenuService extends IService<Menu> {

    List<UserMenuDTO> MenuListByUser();

    List<MenuDTO> listMenus(ConditionVo conditionVo);

    void saveOrUpdateMenu(MenuVo menuVo);


    void deleteMenu(Long menuId);

    List<LabelDTO> getMenuListByRole();

    void updateMenuStatus(StatusVo menuStatusVo);
}
