package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lee.blog.dto.LabelDTO;
import com.lee.blog.dto.ResourceDTO;
import com.lee.blog.entity.Resource;
import com.lee.blog.entity.RoleResource;
import com.lee.blog.mapper.ResourceMapper;
import com.lee.blog.mapper.RoleResourceMapper;
import com.lee.blog.security.FilterInvocationSecurityMetadataSourceImpl;
import com.lee.blog.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.ResourceVo;
import com.lee.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lee.common.comstant.Const.FALSE;

/**
 * <p>
 *  资源权限实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-14
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Autowired
    RoleResourceMapper roleResourceMapper;

    /**
     * 获取资源权限列表信息
     * @return
     */
    @Override
    public List<LabelDTO> getResourceListByRole() {

        // 查询资源列表
        List<Resource> resourceList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getResourceId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getAnonymous, FALSE));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Long, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        List<LabelDTO> collect = parentList.stream().map(item -> {
            List<LabelDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getResourceId());
            if (CollectionUtils.isNotEmpty(children)) {
                //资源列表
                list = children.stream()
                        .map(resource -> LabelDTO.builder()
                                .id(resource.getResourceId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            //资源模块及其下的资源列表
            return LabelDTO.builder()
                    .id(item.getResourceId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 获取资源模块列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<ResourceDTO> getResourcesList(ConditionVo conditionVo) {

        // 模糊查询资源列表
        List<Resource> resourceList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Resource::getResourceName, conditionVo.getKeywords()));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Long, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanUtil.copyProperties(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtil.copyList(childrenMap.get(item.getResourceId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenMap.remove(item.getResourceId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanUtil.copyProperties(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    /**
     * 保存更新资源模块信息
     * @param resourceVo
     */
    @Override
    public void saveOrUpdateResource(ResourceVo resourceVo) {
        // 更新资源信息
        Resource resource = BeanUtil.copyProperties(resourceVo, Resource.class);
        this.saveOrUpdate(resource);
        // 重新加载角色资源信息
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    /**
     * 删除资源模块
     * @param resourceId
     */
    @Override
    public void deleteResource(Long resourceId) {

        // 查询是否有角色关联
        Integer count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new BizException("该资源下存在角色");
        }
        // 查询出该id下的子资源 删除
        List<Long> resourceIdList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getResourceId)
                .eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getResourceId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        resourceMapper.deleteBatchIds(resourceIdList);
    }

    /**
     * 获取资源模块下的所有资源
     * @param resourceList
     * @return
     */
    private Map<Long, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        Map<Long, List<Resource>> collect = resourceList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
        return collect;
    }

    /**
     * 获取所有资源模块
     * @param resourceList
     * @return
     */
    private List<Resource> listResourceModule(List<Resource> resourceList) {
        List<Resource> collect = resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
        return collect;
    }
}
