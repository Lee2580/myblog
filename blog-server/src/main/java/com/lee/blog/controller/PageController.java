package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.service.PageService;
import com.lee.blog.vo.PageVo;
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
 *  前台页面控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
@Api(tags = "页面管理模块")
@RestController
public class PageController {

    @Autowired
    PageService pageService;

    /**
     * 获取页面列表
     * @return
     */
    @ApiOperation(value = "获取页面列表")
    @GetMapping("/admin/listPages")
    public Result listPages() {

        List<PageVo> list= pageService.listPages();
        return Result.success(list);
    }

    /**
     * 保存或更新页面
     * @param page
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新页面")
    @PostMapping("/admin/saveOrUpdatePage")
    public Result saveOrUpdatePage(@Valid @RequestBody PageVo page) {
        pageService.saveOrUpdatePage(page);
        return Result.success();
    }

    /**
     * 根据id删除页面
     * @param pageId
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除页面")
    @DeleteMapping("/admin/deletePage/{pageId}")
    public Result deletePage(@PathVariable("pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return Result.success();
    }
}
