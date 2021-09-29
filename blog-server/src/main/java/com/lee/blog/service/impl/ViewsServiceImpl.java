package com.lee.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.dto.ViewsDTO;
import com.lee.blog.entity.Views;
import com.lee.blog.mapper.ViewsMapper;
import com.lee.blog.service.RedisService;
import com.lee.blog.service.ViewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.lee.common.comstant.RedisConst.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Service
public class ViewsServiceImpl extends ServiceImpl<ViewsMapper, Views> implements ViewsService {

    @Autowired
    ViewsMapper viewsMapper;

    @Autowired
    RedisService redisService;

    /**
     * 查询一周用户访问量
     * @return
     */
    @Override
    public List<ViewsDTO> listViewsByWeek() {

        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        List<ViewsDTO> list = viewsMapper.listViewsByWeek(startTime, endTime);
        return list;
    }

    /**
     * 定期将redis缓存访问量数据存储到mysql
     */
    @Override
    public void redisDataToMySql() {
        //查询
        Views views = viewsMapper.selectOne(new LambdaQueryWrapper<Views>().
                select().orderByDesc(Views::getId).last("limit 1"));
        //查询网站最新浏览量
        Integer viewsCount = (Integer) redisService.get(WEBSITE_VIEWS_COUNT);
        Integer addViewsCount = viewsCount - (views.getTotalVisits()==null?0:views.getTotalVisits());
        Views newViews = Views.builder().viewsCount(addViewsCount).totalVisits(viewsCount).build();
        viewsMapper.insert(newViews);
        //log.info("views ===>"+newViews);
    }
}
