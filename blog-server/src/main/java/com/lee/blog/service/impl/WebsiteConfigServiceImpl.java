package com.lee.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.lee.blog.entity.WebsiteConfig;
import com.lee.blog.mapper.WebsiteConfigMapper;
import com.lee.blog.service.RedisService;
import com.lee.blog.service.WebsiteConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.vo.AboutVo;
import com.lee.blog.vo.WebsiteConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.lee.common.comstant.RedisConst.ABOUT;
import static com.lee.common.comstant.RedisConst.WEBSITE_CONFIG;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
@Slf4j
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig> implements WebsiteConfigService {

    @Autowired
    RedisService redisService;

    @Autowired
    WebsiteConfigMapper websiteConfigMapper;

    /**
     * 获取网站配置
     * @return
     */
    @Transactional
    @Override
    public WebsiteConfigVo getWebsiteConfig() {

        WebsiteConfigVo websiteConfigVo;
        // 获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVo.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigMapper.selectById(1L).getConfig();
            //log.info("config===>"+config);
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVo;
    }

    /**
     * 更新网站配置
     * @param websiteConfig
     */
    @Transactional
    @Override
    public void updateWebsiteConfig(WebsiteConfigVo websiteConfig) {
        String websiteConfigString = JSON.toJSONString(websiteConfig);
        // 修改网站配置保存到数据库
        WebsiteConfig newWebsiteConfig = WebsiteConfig.builder()
                .id(1L)
                .config(websiteConfigString)
                .build();
        websiteConfigMapper.updateById(newWebsiteConfig);
        // 删除旧缓存
        redisService.del(WEBSITE_CONFIG);

    }

    /**
     * 修改关于我的信息
     * @param aboutVo
     */
    @Transactional
    @Override
    public void updateAbout(AboutVo aboutVo) {
        redisService.set(ABOUT, aboutVo.getAboutContent());
    }

    /**
     * 查询关于我的信息
     * @return
     */
    @Override
    public String getAbout() {

        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }
}
