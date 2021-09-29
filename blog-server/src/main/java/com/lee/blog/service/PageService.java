package com.lee.blog.service;

import com.lee.blog.entity.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.PageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
public interface PageService extends IService<Page> {

    List<PageVo> listPages();

    void saveOrUpdatePage(PageVo page);

    void deletePage(Integer pageId);
}
