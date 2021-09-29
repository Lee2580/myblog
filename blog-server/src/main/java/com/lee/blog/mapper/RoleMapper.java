package com.lee.blog.mapper;

import com.lee.blog.dto.ResourceRoleDTO;
import com.lee.blog.dto.RoleDTO;
import com.lee.blog.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> listRolesByUserId(@Param("userId") Long userId);

    List<RoleDTO> listRoles(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("conditionVo") ConditionVo conditionVo);

    List<ResourceRoleDTO> listResourceRoles();
}
