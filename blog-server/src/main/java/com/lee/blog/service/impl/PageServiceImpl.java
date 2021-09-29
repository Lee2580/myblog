package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.lee.blog.entity.Page;
import com.lee.blog.mapper.PageMapper;
import com.lee.blog.service.PageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.service.RedisService;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lee.common.comstant.RedisConst.PAGE_COVER;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {

    @Autowired
    RedisService redisService;

    @Autowired
    PageMapper pageMapper;

    /**
     * 获取页面信息列表
     * @return
     */
    @Transactional
    @Override
    public List<PageVo> listPages() {

        List<PageVo> pageVOList = null;
        // 先查找缓存信息
        Object pageList = redisService.get(PAGE_COVER);
        if (Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            //缓存不存在，从mysql读取
            List<Page> pages = pageMapper.selectList(null);
            pageVOList = BeanCopyUtil.copyList(pages, PageVo.class);
            //更新缓存
            redisService.set(PAGE_COVER, JSON.toJSONString(pageVOList));
        }
        return pageVOList;
    }

    /**
     * 更新保存页面
     * @param pageVo
     */
    @Transactional
    @Override
    public void saveOrUpdatePage(PageVo pageVo) {

        Page page = BeanUtil.copyProperties(pageVo, Page.class);
        this.saveOrUpdate(page);
        // 删除缓存
        redisService.del(PAGE_COVER);

    }

    /**
     * 根据id删除页面
     * @param pageId
     */
    @Transactional
    @Override
    public void deletePage(Integer pageId) {
        pageMapper.deleteById(pageId);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }
}
