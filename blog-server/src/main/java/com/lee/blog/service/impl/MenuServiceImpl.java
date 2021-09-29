package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.MenuDTO;
import com.lee.blog.dto.UserMenuDTO;
import com.lee.blog.entity.Menu;
import com.lee.blog.entity.RoleMenu;
import com.lee.blog.mapper.MenuMapper;
import com.lee.blog.mapper.RoleMenuMapper;
import com.lee.blog.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.service.UserService;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.UserUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.MenuVo;
import com.lee.blog.vo.StatusVo;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lee.common.comstant.Const.COMPONENT;
import static com.lee.common.comstant.Const.TRUE;

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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserService userService;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    /**
     * 获取用户菜单信息
     * @return
     */
    @Override
    public List<UserMenuDTO> MenuListByUser() {
        //查询用户菜单信息
        List<Menu> menuList= menuMapper.listMenusByUserId(UserUtil.getLoginUser().getUserId());
        //log.info("菜单信息 ====> { }"+menuList);
        //获取目录列表
        List<Menu> catalogs = getCatalog(menuList);
        //log.info("目录列表 ====> { }"+catalogs);
        //获取目录下的子菜单
        Map<Long, List<Menu>> childrenMenus= getMenus(menuList);

        return convertUserMenuList(catalogs, childrenMenus);
    }

    /**
     * 条件查询菜单列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<MenuDTO> listMenus(ConditionVo conditionVo) {
        // 模糊查询菜单数据
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Menu::getName, conditionVo.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = getCatalog(menuList);
        // 获取目录下的子菜单
        Map<Long, List<Menu>> childrenMap = getMenus(menuList);
        //log.info("获取目录下的子菜单 ===> " +childrenMap);
        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = catalogList.stream().map(item -> {
            MenuDTO menuDTO = BeanUtil.copyProperties(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> list = BeanCopyUtil.copyList(childrenMap.get(item.getMenuId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            //log.info("目录下的菜单排序 ====》 "+ list);
            menuDTO.setChildren(list);
            childrenMap.remove(item.getMenuId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum))
                .collect(Collectors.toList());
        //log.info("组装目录菜单数据 ====》 "+ menuDTOList);
        // 若还有菜单未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);

            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanUtil.copyProperties(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }
        return menuDTOList;
    }

    /**
     * 保存更新菜单
     * @param menuVo
     */
    @Transactional
    @Override
    public void saveOrUpdateMenu(MenuVo menuVo) {
        Menu menu = BeanUtil.copyProperties(menuVo, Menu.class);
        this.saveOrUpdate(menu);
    }

    /**
     * 删除菜单
     * @param menuId
     */
    @Override
    public void deleteMenu(Long menuId) {
        // 查询是否有角色关联
        Integer count = roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        if (count > 0) {
            throw new BizException("菜单下有角色关联，无法删除");
        }
        // 查询子菜单
        List<Long> menuIdList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getMenuId)
                .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getMenuId)
                .collect(Collectors.toList());
        menuIdList.add(menuId);
        menuMapper.deleteBatchIds(menuIdList);
    }

    /**
     * 查询角色菜单权限列表
     * @return
     */
    @Override
    public List<LabelDTO> getMenuListByRole() {

        // 查询菜单数据
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>()
                .select(Menu::getMenuId, Menu::getName, Menu::getParentId, Menu::getOrderNum,Menu::getType));
        // 获取目录列表
        List<Menu> catalogList = getCatalog(menuList);
        // 获取目录下的子菜单
        Map<Long, List<Menu>> childrenMap = getMenus(menuList);
        // 组装目录菜单数据
        List<LabelDTO> collect = catalogList.stream().map(item -> {
            // 获取目录下的菜单排序
            List<LabelDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getMenuId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelDTO.builder()
                                .id(menu.getMenuId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            //将目录和菜单整合
            return LabelDTO.builder()
                    .id(item.getMenuId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 修改菜单隐藏状态
     * @param menuStatusVo
     */
    @Override
    public void updateMenuStatus(StatusVo menuStatusVo) {
        Menu menu = Menu.builder()
                .menuId(menuStatusVo.getId())
                .status(menuStatusVo.getStatus())
                .build();
        menuMapper.updateById(menu);
    }


    /**
     * 转换用户菜单格式
     * @param catalogs
     * @param childrenMenus
     * @return
     */
    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogs, Map<Long, List<Menu>> childrenMenus) {

        List<UserMenuDTO> collect = catalogs.stream().map(item -> {
            //目录
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            //子菜单
            List<Menu> children = childrenMenus.get(item.getMenuId());
            if (CollectionUtil.isNotEmpty(children)) {
                //子菜单不为空，进行多级菜单处理
                userMenuDTO = BeanUtil.copyProperties(item, UserMenuDTO.class);
                list = children.stream()
                        //排序
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanUtil.copyProperties(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getStatus().equals(TRUE));
                            return dto;
                        }).collect(Collectors.toList());
            } else {
                //子菜单为空，只需进行目录处理
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent(COMPONENT);
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getStatus().equals(TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
        //log.info("convertUserMenuList ===>"+collect);
        return collect;
    }

    /**
     * 获取目录下的子菜单列表
     * @param menuList
     * @return
     */
    private Map<Long, List<Menu>> getMenus(List<Menu> menuList) {
        Map<Long, List<Menu>> collect = menuList.stream().filter(item -> item.getType() == 1)
                //按照ParentId进行分组
                .collect(Collectors.groupingBy(Menu::getParentId));
        return collect;
    }

    /**
     * 获取目录列表
     * @param menuList
     * @return
     */
    private List<Menu> getCatalog(List<Menu> menuList) {
        List<Menu> menus = menuList.stream().filter(item -> item.getType() == 0)
                //对象集合以类属性 升序排序
                .sorted(Comparator.comparing(Menu::getMenuId))
                .collect(Collectors.toList());
        return menus;
    }


}
