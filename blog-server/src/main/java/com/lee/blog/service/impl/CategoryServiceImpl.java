package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.dto.CategoryDTO;
import com.lee.blog.dto.CategoryInfoDTO;
import com.lee.blog.dto.CategoryListDTO;
import com.lee.blog.entity.Blog;
import com.lee.blog.entity.BlogCategory;
import com.lee.blog.entity.Category;
import com.lee.blog.mapper.BlogCategoryMapper;
import com.lee.blog.mapper.CategoryMapper;
import com.lee.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.CategoryVo;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    BlogCategoryMapper blogCategoryMapper;

    @Override
    public List<CategoryDTO> categoryListBySearch(ConditionVo conditionVo) {

        // 搜索分类
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Category::getCategoryName, conditionVo.getKeywords())
                .orderByDesc(Category::getCategoryId));
        return BeanCopyUtil.copyList(categoryList, CategoryDTO.class);
    }

    /**
     * 查询分类列表
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<CategoryListDTO> getCategoryListDTO(ConditionVo conditionVo) {

        // 查询分类数量
        Integer count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Category::getCategoryName, conditionVo.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类列表
        List<CategoryListDTO> categoryList = categoryMapper.listCategoryListDTO(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVo);
        return new PageResult<>(categoryList, count);
    }

    /**
     * 删除分类
     * @param categoryIdList
     */
    @Override
    public void deleteCategories(List<Long> categoryIdList) {
        // 查询分类id下是否有博客
        Integer count = blogCategoryMapper.selectCount(new LambdaQueryWrapper<BlogCategory>()
                .in(BlogCategory::getCategoryId, categoryIdList));
        if (count > 0) {
            throw new BizException("删除失败，该分类下存在博客");
        }
        categoryMapper.deleteBatchIds(categoryIdList);
    }

    /**
     * 更新保存分类
     * @param categoryVo
     */
    @Override
    public void saveOrUpdateCategory(CategoryVo categoryVo) {

        // 判断分类名重复
        Category newCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getCategoryId)
                .eq(Category::getCategoryName, categoryVo.getCategoryName()));
        if (Objects.nonNull(newCategory) && !newCategory.getCategoryId().equals(categoryVo.getCategoryId())) {
            throw new BizException("该分类名已存在");
        }
        Category category = Category.builder()
                .categoryId(categoryVo.getCategoryId())
                .categoryName(categoryVo.getCategoryName())
                .build();
        this.saveOrUpdate(category);
    }

    /**
     * 前台查询分类列表
     * @return
     */
    @Override
    public PageResult<CategoryInfoDTO> listCategories() {

        //查询分类列表及其博客数量
        List<CategoryInfoDTO> categoryInfoDTOList= categoryMapper.listCategoryInfoDTO();
        //查询标签数量
        Integer count = categoryMapper.selectCount(null);

        return new PageResult<>(categoryInfoDTOList, count);
    }
}
