package com.lee.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lee.blog.entity.Resource;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lee
 * @create 2021-09-14 21:16
 **/
@Data
public class ResourceDTO extends Resource {

    /**
     * 权限列表
     */
    private List<ResourceDTO> children;

}
