package com.lee.blog.service;

import com.lee.blog.dto.OperationLogDTO;
import com.lee.blog.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-21
 */
public interface OperationLogService extends IService<OperationLog> {

    PageResult<OperationLogDTO> listOperationLogs(ConditionVo conditionVo);
}
