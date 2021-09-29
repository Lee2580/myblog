package com.lee.blog.service;

import com.lee.blog.entity.WebsiteConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.AboutVo;
import com.lee.blog.vo.WebsiteConfigVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
public interface WebsiteConfigService extends IService<WebsiteConfig> {

    WebsiteConfigVo getWebsiteConfig();

    void updateWebsiteConfig(WebsiteConfigVo websiteConfig);

    void updateAbout(AboutVo aboutVo);

    String getAbout();
}
