package com.lee.blog.mapper;

import com.lee.blog.dto.UserListDTO;
import com.lee.blog.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    Integer countUser(@Param("condition") ConditionVo condition);

    List<UserListDTO> listUsers(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("condition") ConditionVo condition);
}
