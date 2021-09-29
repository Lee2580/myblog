package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 用户分布地区dto
 * @author lee
 * @create 2021-09-17 12:28
 **/
@Data
@Builder
public class UserAreaDTO {

    /**
     * 地区名
     */
    private String name;

    /**
     * 数量
     */
    private Long value;
}
