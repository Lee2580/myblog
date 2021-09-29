package com.lee.blog.service;

import com.lee.blog.dto.ViewsDTO;
import com.lee.blog.entity.Views;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface ViewsService extends IService<Views> {

    List<ViewsDTO> listViewsByWeek();

    void redisDataToMySql();
}
