package com.lee.blog.mapper;

import cn.hutool.core.date.DateTime;
import com.lee.blog.dto.ViewsDTO;
import com.lee.blog.entity.Views;
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
public interface ViewsMapper extends BaseMapper<Views> {

    List<ViewsDTO> listViewsByWeek(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);


    Views getLastData(@Param("nowTime") DateTime nowTime);
}
