package com.lee.blog.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @author lee
 * @create 2021-09-14 19:25
 **/
@Data
public class ResourceRoleDTO {

    /**
     * 资源id
     */
    private Long resourceId;

    /**
     * 路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 角色名
     */
    private List<String> roleList;
}
