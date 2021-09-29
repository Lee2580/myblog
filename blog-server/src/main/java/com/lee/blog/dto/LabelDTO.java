package com.lee.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 权限标签选项
 * @author lee
 * @create 2021-09-14 17:58
 **/
@Data
@Builder
public class LabelDTO {

    /**
     * 选项id
     */
    private Long id;

    /**
     * 选项名
     */
    private String label;

    /**
     * 子选项
     */
    private List<LabelDTO> children;
}
