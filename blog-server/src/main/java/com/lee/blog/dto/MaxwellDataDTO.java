package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * maxwell 监听数据
 * @author lee
 * @create 2021-12-13 16:57
 **/
@Data
@Builder
public class MaxwellDataDTO {

    /**
     * 数据库
     */
    private String database;

    /**
     * xid
     */
    private Integer xid;

    /**
     * 数据
     */
    private Map<String, Object> data;

    /**
     * 是否提交
     */
    private Boolean commit;

    /**
     * 类型
     */
    private String type;

    /**
     * 表
     */
    private String table;

    /**
     * ts
     */
    private Integer ts;

}
