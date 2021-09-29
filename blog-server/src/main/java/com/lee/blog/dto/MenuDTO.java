package com.lee.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lee.blog.entity.Menu;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单dto
 * @author lee
 * @create 2021-09-14 15:49
 **/
@Data
public class MenuDTO {

    /**
     * 主键
     */
    private Long menuId;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 隐藏状态 0否 1是
     */
    private Integer status;

    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    private Integer type;

    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;
}
