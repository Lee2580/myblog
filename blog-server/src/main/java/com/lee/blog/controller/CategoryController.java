package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.CategoryInfoDTO;
import com.lee.blog.dto.CategoryListDTO;
import com.lee.blog.service.CategoryService;
import com.lee.blog.vo.CategoryVo;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.REMOVE;
import static com.lee.common.comstant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  分类模块控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-15
 */
@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*******************************************
     *                  后台操作                *
     * *****************************************
     */

    /**
     * admin查询分类列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "admin查询分类列表")
    @GetMapping("/admin/categories")
    public Result getCategoryList( ConditionVo conditionVo) {
        PageResult<CategoryListDTO> list= categoryService.getCategoryListDTO(conditionVo);
        return Result.success(list);
    }

    /**
     * 根据id删除分类
     * @param categoryIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/admin/deleteCategories")
    public Result deleteCategories(@RequestBody List<Long> categoryIdList) {
        categoryService.deleteCategories(categoryIdList);
        return Result.success();
    }

    /**
     * 保存更新分类
     * @param categoryVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新分类")
    @PostMapping("/admin/saveOrUpdateCategory")
    public Result saveOrUpdateCategory(@Valid @RequestBody CategoryVo categoryVo) {
        categoryService.saveOrUpdateCategory(categoryVo);
        return Result.success();
    }

    /*******************************************
     *                 前台操作                 *
     * *****************************************
     */

    /**
     * 查询分类列表
     * @return
     */
    @ApiOperation(value = "前台查询分类列表")
    @GetMapping("/categories")
    public Result listCategories() {
        PageResult<CategoryInfoDTO> list = categoryService.listCategories();
        return Result.success(list);
    }

}
