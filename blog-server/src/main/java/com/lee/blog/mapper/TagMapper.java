package com.lee.blog.mapper;

import com.lee.blog.dto.TagListDTO;
import com.lee.blog.entity.Tag;
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
public interface TagMapper extends BaseMapper<Tag> {

    List<String> listTagNameByBlogId(@Param("blogId") Long blogId);

    List<TagListDTO> getTagListDTO(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("conditionVo") ConditionVo conditionVo);
}
