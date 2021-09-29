package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.blog.dto.*;
import com.lee.blog.entity.*;
import com.lee.blog.mapper.*;
import com.lee.blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.strategy.impl.MySqlSearchStrategyImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.util.UserUtil;
import com.lee.blog.vo.*;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.lee.common.comstant.RedisConst.*;
import static com.lee.common.comstant.Const.*;
import static com.lee.common.enums.BlogStatusEnum.DRAFT;
import static com.lee.common.enums.BlogStatusEnum.PUBLIC;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogService blogService;

    @Autowired
    BlogTagMapper blogTagMapper;

    @Autowired
    BlogCategoryMapper blogCategoryMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BlogCategoryService blogCategoryService;

    @Autowired
    BlogTagService blogTagService;

    @Autowired
    HttpSession session;

    @Autowired
    MySqlSearchStrategyImpl mySqlSearchStrategy;

    /**
     * 逻辑删除或恢复，修改删除状态
     * @param deleteVo
     */
    @Transactional
    @Override
    public void restoreOrDeleteBlog(DeleteVo deleteVo) {
        // 修改博客逻辑删除状态
        List<Blog> blogList = deleteVo.getIdList().stream()
                .map(id -> Blog.builder()
                        .blogId(id)
                        .recommend(FALSE)
                        .isDelete(deleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        // 更新
        blogService.updateBatchById(blogList);
    }

    /**
     * 物理删除博客
     * @param blogIdList
     */
    @Transactional
    @Override
    public void deleteBlogs(List<Long> blogIdList) {
        // 删除博客标签关联
        blogTagMapper.delete(new LambdaQueryWrapper<BlogTag>()
                .in(BlogTag::getBlogId, blogIdList));
        // 删除博客类型关联
        blogCategoryMapper.delete(new LambdaQueryWrapper<BlogCategory>()
                .in(BlogCategory::getBlogId, blogIdList));
        // 删除博客
        blogMapper.deleteBatchIds(blogIdList);
    }

    /**
     * 修改博客推荐状态
     * @param blogRecommendVo
     */
    @Override
    public void updateBlogRecommend(BlogRecommendVo blogRecommendVo) {

        Blog blog = Blog.builder().blogId(blogRecommendVo.getBlogId()).
                recommend(blogRecommendVo.getRecommend()).build();
        blogMapper.updateById(blog);
    }

    /**
     * 查询博客列表信息
     * @param condition
     * @return
     */
    @Override
    public PageResult<BlogDTO> listBlogs(ConditionVo condition) {

        // 查询博客总量
        Integer count = blogMapper.countBlogs(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        // 查询博客
        List<BlogDTO> blogDTOList = blogMapper.listBlogsByAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), condition);
        //log.info("blogDTOList ==>"+blogDTOList);
        // 查询博客点赞量和浏览量
        Map<Object, Double> viewsCountMap = redisService.zAllScore(BLOG_VIEWS_COUNT);
        //log.info("viewsCountMap ===>"+viewsCountMap);
        Map<String, Object> likeCountMap = redisService.hGetAll(BLOG_LIKE_COUNT);
        //log.info("likeCountMap ===>"+likeCountMap);
        // 封装点赞量和浏览量
        blogDTOList.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getBlogId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
            Object likeCount = likeCountMap.get(item.getBlogId().toString());
            item.setLikeCount((Integer) likeCount);
        });
        return new PageResult<>(blogDTOList, count);
    }

    /**
     * admin查询某个博客信息
     * @param blogId
     * @returnq
     */
    @Override
    public BlogVo getBlogById(Long blogId) {

        // 查询博客信息
        Blog blog = blogMapper.selectById(blogId);
        // 查询博客分类
        String categoryName = categoryMapper.CategoryNameByBlogId(blogId);
        // 查询博客标签
        List<String> tagNameList = tagMapper.listTagNameByBlogId(blogId);
        // 封装数据
        BlogVo blogVo = BeanUtil.copyProperties(blog, BlogVo.class);
        blogVo.setCategoryName(categoryName);
        blogVo.setTagNameList(tagNameList);
        return blogVo;
    }

    /**
     * 更新保存博客
     * @param blogVo
     */
    @Transactional
    @Override
    public void saveOrUpdateBlog(BlogVo blogVo) {

        // 保存或修改博客
        Blog blog = BeanUtil.copyProperties(blogVo, Blog.class);
        blog.setUserId(UserUtil.getLoginUser().getUserId());
        //log.info("blog ==>"+blog);
        blogService.saveOrUpdate(blog);
        // 保存博客分类
        saveBlogCategory(blogVo, blog.getBlogId());
        // 保存博客标签
        saveBlogTag(blogVo, blog.getBlogId());
    }

    /**
     * 根据id查询博客
     * @param blogId
     * @return
     */
    @Override
    public BlogInfoDTO getBlogsById(Long blogId) {

        // 查询推荐博客
        CompletableFuture<List<BlogRecommendDTO>> recommendBlogList = CompletableFuture
                .supplyAsync(() -> blogMapper.listRecommendBlog(blogId));
        // 查询最新博客
        CompletableFuture<List<BlogRecommendDTO>> newBlogList = CompletableFuture
                .supplyAsync(() -> {
                    List<Blog> blogList = blogMapper.selectList(new LambdaQueryWrapper<Blog>()
                            .select(Blog::getBlogId, Blog::getTitle, Blog::getFirstPicture, Blog::getCreateTime)
                            .eq(Blog::getIsDelete, FALSE)
                            .eq(Blog::getStatus, PUBLIC.getStatus())
                            .orderByDesc(Blog::getBlogId)
                            .last("limit 5"));
                    return BeanCopyUtil.copyList(blogList, BlogRecommendDTO.class);
                });
        // 查询id对应博客
        BlogInfoDTO blog = blogMapper.getBlogById(blogId);
        if (Objects.isNull(blog)) {
            throw new BizException("博客不存在");
        }
        // 更新博客浏览量
        updateBlogViewsCount(blogId);
        // 查询上一篇下一篇博客
        Blog lastBlog = blogMapper.selectOne(new LambdaQueryWrapper<Blog>()
                .select(Blog::getBlogId, Blog::getTitle, Blog::getFirstPicture)
                .eq(Blog::getIsDelete, FALSE)
                .eq(Blog::getStatus, PUBLIC.getStatus())
                .lt(Blog::getBlogId, blogId)
                .orderByDesc(Blog::getBlogId)
                .last("limit 1"));
        Blog nextBlog = blogMapper.selectOne(new LambdaQueryWrapper<Blog>()
                .select(Blog::getBlogId, Blog::getTitle, Blog::getFirstPicture)
                .eq(Blog::getIsDelete, FALSE)
                .eq(Blog::getStatus, PUBLIC.getStatus())
                .gt(Blog::getBlogId, blogId)
                .orderByAsc(Blog::getBlogId)
                .last("limit 1"));

        blog.setLastBlog(BeanUtil.copyProperties(lastBlog, BlogPaginationDTO.class));
        blog.setNextBlog(BeanUtil.copyProperties(nextBlog, BlogPaginationDTO.class));

        // 封装点赞量和浏览量
        Double score = redisService.zScore(BLOG_VIEWS_COUNT, blogId);
        if (Objects.nonNull(score)) {
            blog.setViewsCount(score.intValue());
        }
        blog.setLikeCount((Integer) redisService.hGet(BLOG_LIKE_COUNT, blogId.toString()));

        // 封装博客信息
        try {
            blog.setRecommendBlogList(recommendBlogList.get());
            blog.setNewBlogList(newBlogList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blog;
    }

    /**
     * 搜索博客
     * @param conditionVo
     * @return
     */
    @Override
    public List<BlogSearchDTO> listBlogBySearch(ConditionVo conditionVo) {

        List<BlogSearchDTO> blogSearchDTOS = mySqlSearchStrategy.searchBlogs(conditionVo.getKeywords());
        return blogSearchDTOS;
    }

    /**
     * 查询首页博客
     * @return
     */
    @Override
    public List<HomeBlogDTO> listHomeBlog() {

        return blogMapper.listHomeBlog(PageUtil.getLimitCurrent(), PageUtil.getSize());
    }

    /**
     * 点赞/取消 博客
     * @param blogId
     */
    @Transactional
    @Override
    public void saveBlogLike(Long blogId) {
        // 判断是否点赞
        String blogLikeKey = BLOG_USER_LIKE + UserUtil.getLoginUser().getUserId();
        if (redisService.sIsMember(blogLikeKey, blogId)) {
            // 点过赞则删除博客id
            redisService.sRemove(blogLikeKey, blogId);
            // 博客点赞量-1
            redisService.hDecr(BLOG_LIKE_COUNT, blogId.toString(), 1L);
        } else {
            // 未点赞则增加博客id
            redisService.sAdd(blogLikeKey, blogId);
            // 博客点赞量+1
            redisService.hIncr(BLOG_LIKE_COUNT, blogId.toString(), 1L);
        }
    }

    /**
     * 查询博客归档
     * @return
     */
    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Blog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        // 获取分页数据
        Page<Blog> articlePage = blogMapper.selectPage(page, new LambdaQueryWrapper<Blog>()
                .select(Blog::getBlogId, Blog::getTitle, Blog::getCreateTime)
                .orderByDesc(Blog::getCreateTime)
                .eq(Blog::getIsDelete, FALSE)
                .eq(Blog::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtil.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.getTotal());
    }

    /**
     * 根据条件查询博客预览列表
     * @param condition
     * @return
     */
    @Override
    public BlogPreviewListDTO listBlogByCondition(ConditionVo condition) {

        // 搜索博客
        List<BlogPreviewDTO> blogPreviewDTOList = blogMapper.listArticlesByCondition(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), condition);

        // 搜索条件对应名(标签或分类名)
        /**
         * 实体类没有提供无参构造函数导致查询时字段映射失败
         * Category和Tag 加上有参无参构造  解决以下报错：
         *      Error attempting to get column 'tag_name' from result set.
         *      Cause: java.sql.SQLDataException: Cannot determine value type from string '测试标签'
         */
        String name = null;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getCategoryId, condition.getCategoryId()))
                    .getCategoryName();
        } else {
            name = tagService.getOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getTagId, condition.getTagId()))
                    .getTagName();
        }
        return BlogPreviewListDTO.builder()
                .blogPreviewDTOList(blogPreviewDTOList)
                .name(name)
                .build();
    }

    /**
     * 更新博客浏览量
     * @param blogId
     */
    private void updateBlogViewsCount(Long blogId) {

        // 判断是否第一次访问，增加浏览量
        Set<Long> blogSet = (Set<Long>) Optional.ofNullable(session.getAttribute(BLOG_SET))
                .orElse(new HashSet<>());
        if (!blogSet.contains(blogId)) {
            blogSet.add(blogId);
            session.setAttribute(BLOG_SET, blogSet);
            // 浏览量+1
            redisService.zIncr(BLOG_VIEWS_COUNT, blogId, 1D);
        }
    }

    /**
     * 保存博客分类
     * @param blogVo
     * @return
     */
    private void saveBlogCategory(BlogVo blogVo, Long blogId) {
        // 编辑博客则删除博客分类
        if (Objects.nonNull(blogVo.getBlogId())) {
            blogCategoryMapper.delete(new LambdaQueryWrapper<BlogCategory>()
                    .eq(BlogCategory::getBlogId, blogVo.getBlogId()));
        }
        // 判断分类是否存在
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, blogVo.getCategoryName()));
        // 不存在添加分类
        if (Objects.isNull(category) && !blogVo.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder()
                    .categoryName(blogVo.getCategoryName())
                    .build();
            categoryMapper.insert(category);
        }
        // 绑定博客分类
        if (Objects.nonNull(category)) {
            BlogCategory blogCategory = BlogCategory.builder()
                    .categoryId(category.getCategoryId())
                    .blogId(blogId).build();
            blogCategoryMapper.insert(blogCategory);
        }
    }

    /**
     * 保存博客标签
     * @param blogVo
     * @param blogId
     */
    private void saveBlogTag(BlogVo blogVo, Long blogId) {
        // 编辑博客则删除博客所有标签
        if (Objects.nonNull(blogVo.getBlogId())) {
            blogTagMapper.delete(new LambdaQueryWrapper<BlogTag>()
                    .eq(BlogTag::getBlogId, blogVo.getBlogId()));
        }
        // 添加博客标签
        List<String> tagNameList = blogVo.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>()
                    .in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList());
            List<Long> existTagIdList = existTagList.stream()
                    .map(Tag::getTagId)
                    .collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder()
                        .tagName(item)
                        .build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tagList);
                List<Long> tagIdList = tagList.stream()
                        .map(Tag::getTagId)
                        .collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定博客
            List<BlogTag> blogTagList = existTagIdList.stream().map(item ->
                    BlogTag.builder()
                    .blogId(blogId)
                    .tagId(item)
                    .build())
                    .collect(Collectors.toList());
            blogTagService.saveBatch(blogTagList);
        }
    }



}
