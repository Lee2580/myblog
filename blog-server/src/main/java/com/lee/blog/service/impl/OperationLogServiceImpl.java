package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.blog.dto.OperationLogDTO;
import com.lee.blog.entity.OperationLog;
import com.lee.blog.mapper.OperationLogMapper;
import com.lee.blog.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-21
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    /**
     * 查询操作日志列表
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<OperationLogDTO> listOperationLogs(ConditionVo conditionVo) {

        Page<OperationLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        // 查询日志列表
        Page<OperationLog> operationLogPage = this.page(page, new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), OperationLog::getOptModule, conditionVo.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), OperationLog::getOptDesc, conditionVo.getKeywords())
                .orderByDesc(OperationLog::getId));
        List<OperationLogDTO> operationLogDTOList = BeanCopyUtil.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageResult<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }
}
