package com.lee.blog.dto;

import com.lee.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户菜单DTO
 * @author lee
 * @create 2021-09-14 12:04
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 隐藏状态
     */
    private Boolean hidden;

    /**
     * 子菜单列表
     */
    private List<UserMenuDTO> children;
}
