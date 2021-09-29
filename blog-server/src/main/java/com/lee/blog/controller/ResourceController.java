package com.lee.blog.controller;



import com.lee.blog.dto.ResourceDTO;
import com.lee.blog.entity.Resource;
import com.lee.blog.service.ResourceService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.ResourceVo;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  资源模块控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-14
 */
@Api(tags = "资源管理模块")
@RestController
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    /**
     * 查询资源模块列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "查询资源模块列表")
    @GetMapping("/admin/resources")
    public Result getResourcesList( ConditionVo conditionVo) {

        List<ResourceDTO> resourcesList= resourceService.getResourcesList(conditionVo);
        return Result.success(resourcesList);
    }

    /**
     * 新增或修改资源模块
     * @param resourceVo
     * @return
     */
    @ApiOperation(value = "新增或修改资源模块")
    @PostMapping("/admin/saveOrUpdateResource")
    public Result saveOrUpdateResource(@RequestBody @Valid ResourceVo resourceVo) {
        resourceService.saveOrUpdateResource(resourceVo);
        return Result.success();
    }

    /**
     * 删除资源模块
     * @param resourceId
     * @return
     */
    @ApiOperation(value = "删除资源模块")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result deleteResource(@PathVariable("resourceId") Long resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success();
    }
}
