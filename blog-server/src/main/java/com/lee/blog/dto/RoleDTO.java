package com.lee.blog.dto;

import com.lee.blog.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author lee
 * @create 2021-09-14 17:09
 **/
@Data
public class RoleDTO extends Role{

    /**
     * 资源id列表
     */
    private List<Long> resourceIdList;

    /**
     * 菜单id列表
     */
    private List<Long> menuIdList;
}
