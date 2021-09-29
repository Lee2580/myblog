package com.lee.blog.service;

import com.lee.blog.dto.CategoryDTO;
import com.lee.blog.dto.CategoryInfoDTO;
import com.lee.blog.dto.CategoryListDTO;
import com.lee.blog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.CategoryVo;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-15
 */
public interface CategoryService extends IService<Category> {

    List<CategoryDTO> categoryListBySearch(ConditionVo conditionVo);

    PageResult<CategoryListDTO> getCategoryListDTO(ConditionVo conditionVo);

    void deleteCategories(List<Long> categoryIdList);

    void saveOrUpdateCategory(CategoryVo categoryVo);

    PageResult<CategoryInfoDTO> listCategories();
}
