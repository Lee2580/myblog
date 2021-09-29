package com.lee.blog.service;

import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.ResourceDTO;
import com.lee.blog.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.ResourceVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-14
 */
public interface ResourceService extends IService<Resource> {

    List<LabelDTO> getResourceListByRole();

    List<ResourceDTO> getResourcesList(ConditionVo conditionVo);

    void saveOrUpdateResource(ResourceVo resourceVo);

    void deleteResource(Long resourceId);
}
