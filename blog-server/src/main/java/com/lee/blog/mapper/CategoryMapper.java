package com.lee.blog.mapper;

import com.lee.blog.dto.CategoryDTO;
import com.lee.blog.dto.CategoryInfoDTO;
import com.lee.blog.dto.CategoryListDTO;
import com.lee.blog.entity.Category;
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
 * @since 2021-09-15
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    String CategoryNameByBlogId(@Param("blogId") Long blogId);

    List<CategoryListDTO> listCategoryListDTO(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("conditionVo") ConditionVo conditionVo);

    List<CategoryDTO> listCategoryDTO();

    List<CategoryInfoDTO> listCategoryInfoDTO();
}
