package com.lee.blog.controller;


import com.lee.blog.dto.OperationLogDTO;
import com.lee.blog.service.OperationLogService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  日志控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-21
 */
@Api(tags = "日志模块")
@RestController
public class OperationLogController {

    @Autowired
    OperationLogService operationLogService;

    /**
     * 查看操作日志
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result listOperationLogs( ConditionVo conditionVo) {
        PageResult<OperationLogDTO> list= operationLogService.listOperationLogs(conditionVo);
        return Result.success(list);
    }

    /**
     * 删除操作日志
     * @param logIdList
     * @return
     */
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/admin/deleteOperation/logs")
    public Result deleteOperationLogs(@RequestBody List<Long> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.success();
    }
}
