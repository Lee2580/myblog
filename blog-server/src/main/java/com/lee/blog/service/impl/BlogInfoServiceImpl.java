package com.lee.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lee.blog.dto.*;
import com.lee.blog.entity.Blog;
import com.lee.blog.mapper.*;
import com.lee.blog.service.BlogInfoService;
import com.lee.blog.service.PageService;
import com.lee.blog.service.RedisService;
import com.lee.blog.service.ViewsService;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.IpUtil;
import com.lee.blog.vo.PageVo;
import com.lee.blog.vo.WebsiteConfigVo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.lee.common.comstant.RedisConst.*;
import static com.lee.common.comstant.Const.*;
import static com.lee.common.enums.BlogStatusEnum.PUBLIC;

/**
 * @author lee
 * @create 2021-09-17 10:45
 **/
@Slf4j
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    RedisService redisService;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    ViewsService viewsService;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    WebsiteConfigMapper websiteConfigMapper;

    @Resource
    HttpServletRequest request;

    @Autowired
    PageService pageService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询博客后台信息
     * @return
     */
    @Override
    public BlogAdminInfoDTO getBlogAdminInfo() {

        // 查询访问量
        Integer viewsCount = (Integer) redisService.get(WEBSITE_VIEWS_COUNT);
        // 查询留言量
        Integer messageCount = messageMapper.selectCount(null);
        // 查询用户量
        Integer userCount = userMapper.selectCount(null);
        // 查询文章量
        Integer blogCount = blogMapper.selectCount(new LambdaQueryWrapper<Blog>()
                .eq(Blog::getIsDelete, FALSE));
        // 查询一周用户访问量
        List<ViewsDTO> viewsList = viewsService.listViewsByWeek();
        // 查询博客统计
        List<BlogStatisticsDTO> blogStatisticsDTOList = blogMapper.listBlogStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(tagMapper.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> blogMap = redisService.zReverseRangeWithScore(BLOG_VIEWS_COUNT, 0, 4);
        //log.info("blogMap ===>"+blogMap);
        // 文章为空直接返回
        if (CollectionUtils.isEmpty(blogMap)) {
            return BlogAdminInfoDTO.builder()
                    .blogStatisticsList(blogStatisticsDTOList)
                    .tagDTOList(tagDTOList)
                    .viewsCount(viewsCount)
                    .messageCount(messageCount)
                    .userCount(userCount)
                    .blogCount(blogCount)
                    .categoryDTOList(categoryDTOList)
                    .viewsDTOList(viewsList)
                    .build();
        }

        // 查询博客数据
        List<Long> blogIdList = new ArrayList<>();
        blogMap.forEach((key, value) -> blogIdList.add(((Integer) key).longValue()));
        //log.info("blogIdList ==>"+blogIdList);
        /**
         * blogMap 是Map<Object, Double>类型，Key是Object用Long类型取会报空指针异常
         *          要先转为int型
         * 上面的key为Object类型直接转long型会报错，要先转为String或者Integer再转long
         */
        List<BlogRankDTO> blogRankDTOList = blogMapper.selectList(new LambdaQueryWrapper<Blog>()
                .select(Blog::getBlogId, Blog::getTitle)
                .in(Blog::getBlogId, blogIdList))
                .stream().map(blog -> BlogRankDTO.builder()
                        .title(blog.getTitle())
                        .viewsCount(blogMap.get(blog.getBlogId().intValue()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(BlogRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
        //log.info("blogRankDTOList ===>" +blogRankDTOList);
        return BlogAdminInfoDTO.builder()
                .blogStatisticsList(blogStatisticsDTOList)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .blogCount(blogCount)
                .tagDTOList(tagDTOList)
                .categoryDTOList(categoryDTOList)
                .viewsDTOList(viewsList)
                .blogRankDTOList(blogRankDTOList)
                .build();
    }

    /**
     * 获取网站配置信息
     * @return
     */
    @Override
    public WebsiteConfigVo getWebsiteConfig() {

        WebsiteConfigVo websiteConfigVo;
        // 缓存中有，则获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVo.class);
        } else {
            // 无，从数据库中加载
            String config = websiteConfigMapper.selectById(1).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVo;
    }

    /**
     * 上传访客信息
     */
    @Override
    public void report() {

        // 获取ip
        String ipAddress = IpUtil.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        // 生成唯一用户标识
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisService.incr(WEBSITE_VIEWS_COUNT, 1);
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    /**
     * 查询博客首页信息
     * @return
     */
    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {

        // 查询博客数量
        Integer blogCount = blogMapper.selectCount(new LambdaQueryWrapper<Blog>()
                .eq(Blog::getStatus, PUBLIC.getStatus())
                .eq(Blog::getIsDelete, FALSE));
        // 查询分类数量
        Integer categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagMapper.selectCount(null);
        // 查询访问量
        // 如果没有这个key就先设置上,因为null.toString会报空指针异常
        if (!redisTemplate.hasKey(WEBSITE_VIEWS_COUNT)){
            redisTemplate.opsForValue().set(WEBSITE_VIEWS_COUNT, 0);
        }
        String viewsCount = (redisService.get(WEBSITE_VIEWS_COUNT)).toString();
        // 查询网站配置
        WebsiteConfigVo websiteConfig = this.getWebsiteConfig();
        // 查询页面图片
        List<PageVo> pageVOList = pageService.listPages();
        // 封装数据
        return BlogHomeInfoDTO.builder()
                .blogCount(blogCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }
}
