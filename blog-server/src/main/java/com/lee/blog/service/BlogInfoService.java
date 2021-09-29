package com.lee.blog.service;

import com.lee.blog.dto.BlogAdminInfoDTO;
import com.lee.blog.dto.BlogHomeInfoDTO;
import com.lee.blog.vo.WebsiteConfigVo;

/**
 * @author lee
 * @create 2021-09-17 10:45
 **/
public interface BlogInfoService {

    BlogAdminInfoDTO getBlogAdminInfo();

    WebsiteConfigVo getWebsiteConfig();

    void report();

    BlogHomeInfoDTO getBlogHomeInfo();
}
