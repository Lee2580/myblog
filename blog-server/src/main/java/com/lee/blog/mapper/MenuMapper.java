package com.lee.blog.mapper;

import com.lee.blog.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> listMenusByUserId(@Param("userId") Long userId);
}
