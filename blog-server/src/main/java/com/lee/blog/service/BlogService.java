package com.lee.blog.service;

import com.lee.blog.dto.*;
import com.lee.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.*;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface BlogService extends IService<Blog> {

    void restoreOrDeleteBlog(DeleteVo deleteVo);

    void deleteBlogs(List<Long> blogIdList);

    void updateBlogRecommend(BlogRecommendVo blogRecommendVo);

    PageResult<BlogDTO> listBlogs(ConditionVo conditionVo);

    BlogVo getBlogById(Long blogId);

    void saveOrUpdateBlog(BlogVo blogVo);

    BlogInfoDTO getBlogsById(Long blogId);

    List<BlogSearchDTO> listBlogBySearch(ConditionVo conditionVo);

    List<HomeBlogDTO> listHomeBlog();

    void saveBlogLike(Long blogId);

    PageResult<ArchiveDTO> listArchives();

    BlogPreviewListDTO listBlogByCondition(ConditionVo condition);

    void redisBlogDataToMySql();
}
