package com.lee.blog.mapper;

import com.lee.blog.dto.*;
import com.lee.blog.entity.Blog;
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
public interface BlogMapper extends BaseMapper<Blog> {

    Integer countBlogs(@Param("condition") ConditionVo condition);


    List<BlogDTO> listBlogsByAdmin(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("condition") ConditionVo condition);

    List<BlogStatisticsDTO> listBlogStatistics();

    List<BlogRecommendDTO> listRecommendBlog(@Param("blogId") Long blogId);

    BlogInfoDTO getBlogById(@Param("blogId") Long blogId);

    List<HomeBlogDTO> listHomeBlog(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size);

    List<BlogPreviewDTO> listArticlesByCondition(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("condition") ConditionVo condition);
}
