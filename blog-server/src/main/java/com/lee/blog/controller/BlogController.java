package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.*;
import com.lee.blog.service.BlogService;
import com.lee.blog.service.CategoryService;
import com.lee.blog.service.TagService;
import com.lee.blog.strategy.UploadStrategyContext;
import com.lee.blog.vo.*;
import com.lee.common.Result.Result;
import com.lee.common.enums.FilePathEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.*;

/**
 * <p>
 *  博客控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "博客管理模块")
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    @Autowired
    UploadStrategyContext uploadStrategyContext;

    /**************************************
     *              博客写作               *
     **************************************
     */

    /**
     * admin根据id查询博客
     * @param blogId
     * @return
     */
    @ApiOperation(value = "admin根据id查询博客")
    @GetMapping("/admin/blogs/{blogId}")
    public Result getBlogByIdAdmin(@PathVariable("blogId") Long blogId) {
        BlogVo blog = blogService.getBlogById(blogId);
        return Result.success(blog);
    }

    /**
     * 上传博客图片
     * @param file
     * @return
     */
    @ApiOperation(value = "上传博客图片")
    @PostMapping("/admin/blogs/images")
    public Result saveBlogImages(MultipartFile file) {
        String photo = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.BLOG.getPath());
        return Result.success(photo);
    }

    /**
     * 保存或修改博客
     * @param blogVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或修改博客")
    @PostMapping("/admin/saveOrUpdateBlog")
    public Result saveOrUpdateBlog(@Valid @RequestBody BlogVo blogVo) {
        blogService.saveOrUpdateBlog(blogVo);
        return Result.success();
    }


    /**************************************
     *              博客列表               *
     **************************************
     */

    /**
     * admin查询博客列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "admin查询博客列表")
    @GetMapping("/admin/listBlogs")
    public Result listBlogs(ConditionVo conditionVo) {
        PageResult<BlogDTO> listBlogs= blogService.listBlogs(conditionVo);
        return Result.success(listBlogs);
    }

    /**
     * 逻辑删除博客 —— 恢复或删除
     * @param deleteVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "恢复或删除博客")
    @PutMapping("/admin/restoreOrDeleteBlog")
    public Result restoreOrDeleteBlog(@Valid @RequestBody DeleteVo deleteVo) {
        blogService.restoreOrDeleteBlog(deleteVo);
        return Result.success();
    }

    /**
     * 删除博客
     * @param blogIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "物理删除博客")
    @DeleteMapping("/admin/deleteBlogs")
    public Result deleteBlogs(@RequestBody List<Long> blogIdList) {
        blogService.deleteBlogs(blogIdList);
        return Result.success();
    }

    /**
     * 修改博客推荐置顶状态
     * @param blogRecommendVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改博客推荐置顶状态")
    @PutMapping("/admin/blog/recommend")
    public Result updateBlogRecommend(@Valid @RequestBody BlogRecommendVo blogRecommendVo) {
        blogService.updateBlogRecommend(blogRecommendVo);
        return Result.success();
    }

    /**
     * 获取博客分类列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "获取博客分类列表")
    @GetMapping("/admin/categories/search")
    public Result categoryListBySearch( ConditionVo conditionVo) {
        List<CategoryDTO> list = categoryService.categoryListBySearch(conditionVo);
        return Result.success(list);
    }

    /**
     * 获取博客标签列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "获取博客标签列表")
    @GetMapping("/admin/tags/search")
    public Result tagListBySearch( ConditionVo conditionVo) {
        List<TagDTO> list = tagService.tagListBySearch(conditionVo);
        return Result.success(list);
    }

    /**
     * 根据id查询博客信息
     * @param blogId
     * @return
     */
    @ApiOperation(value = "根据id查询博客")
    @GetMapping("/blogs/{blogId}")
    public Result getBlogById(@PathVariable("blogId") Long blogId) {
        BlogInfoDTO blogInfoDTO = blogService.getBlogsById(blogId);
        return Result.success(blogInfoDTO);
    }

    /**************************************
     *              博客前台               *
     **************************************
     */

    /**
     * 搜索博客
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "搜索博客")
    @GetMapping("/blogs/search")
    public Result listBlogsBySearch( ConditionVo conditionVo) {
        List<BlogSearchDTO> list= blogService.listBlogBySearch(conditionVo);
        return Result.success(list);
    }

    /**
     * 查询首页博客
     * @return
     */
    @ApiOperation(value = "查询首页博客")
    @GetMapping("/blogs")
    public Result listArticles() {
        List<HomeBlogDTO> list= blogService.listHomeBlog();
        return Result.success(list);
    }

    /**
     * 点赞/取消 博客
     * @param blogId
     * @return
     */
    @ApiOperation(value = "点赞博客")
    @PostMapping("/blogs/{blogId}/like")
    public Result saveBlogLike(@PathVariable("blogId") Long blogId) {
        blogService.saveBlogLike(blogId);
        return Result.success();
    }

    /**
     * 查看博客归档
     * @return
     */
    @ApiOperation(value = "查看博客归档")
    @GetMapping("/blogs/archives")
    public Result listArchives() {
        PageResult<ArchiveDTO> listArchives = blogService.listArchives();
        return Result.success(listArchives);
    }

    /**
     * 根据条件查询博客
     * @param condition
     * @return
     */
    @ApiOperation(value = "根据条件查询博客")
    @GetMapping("/blogs/condition")
    public Result listBlogsByCondition(ConditionVo condition) {
        BlogPreviewListDTO list = blogService.listBlogByCondition(condition);
        return Result.success(list);
    }

}
