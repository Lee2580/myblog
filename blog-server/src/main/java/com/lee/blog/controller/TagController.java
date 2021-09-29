package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.TagDTO;
import com.lee.blog.dto.TagListDTO;
import com.lee.blog.service.TagService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.TagVo;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.REMOVE;
import static com.lee.common.comstant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  标签模块控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    TagService tagService;

    /**************************************
     *              博客后台               *
     **************************************
     */

    /**
     * admin查询标签列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "admin查询标签列表")
    @GetMapping("/admin/tags")
    public Result getTagList( ConditionVo conditionVo) {
        PageResult<TagListDTO> list= tagService.getTagListDTO(conditionVo);
        return Result.success(list);
    }

    /**
     * 删除标签
     * @param tagIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/deleteTag")
    public Result deleteTag(@RequestBody List<Long> tagIdList) {
        //log.info("idList ==>"+tagIdList);
        tagService.deleteTag(tagIdList);
        return Result.success();
    }

    /**
     * 添加修改标签
     * @param tagVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/saveOrUpdateTag")
    public Result saveOrUpdateTag(@Valid @RequestBody TagVo tagVo) {
        tagService.saveOrUpdateTag(tagVo);
        return Result.success();
    }

    /**************************************
     *              博客前台               *
     **************************************
     */

    /**
     * 查询标签列表
     * @return
     */
    @ApiOperation(value = "查询标签列表")
    @GetMapping("/tags")
    public Result listTags() {
        PageResult<TagDTO> list = tagService.listTags();
        return Result.success(list);
    }

}
