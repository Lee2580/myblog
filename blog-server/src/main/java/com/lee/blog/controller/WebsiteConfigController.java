package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.service.WebsiteConfigService;
import com.lee.blog.vo.AboutVo;
import com.lee.blog.vo.WebsiteConfigVo;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lee.common.comstant.OptTypeConst.UPDATE;

/**
 * <p>
 *  网站配置控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-16
 */
@Api(tags = "网站配置模块")
@RestController
public class WebsiteConfigController {

    @Autowired
    WebsiteConfigService websiteConfigService;

    /**************************************
     *              网站管理               *
     * ************************************
     */

    /**
     * 获取网站配置
     * @return
     */
    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/getConfig")
    public Result getWebsiteConfig() {

         WebsiteConfigVo config = websiteConfigService.getWebsiteConfig();
        return Result.success(config);
    }

    /**
     * 更新网站配置
     * @param websiteConfig
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/updateConfig")
    public Result updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVo websiteConfig) {
        websiteConfigService.updateWebsiteConfig(websiteConfig);
        return Result.success();
    }

    /**************************************
     *               关于我                *
     * ************************************
     */

    /**
     * 修改关于我的信息
     * @param aboutVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我的信息")
    @PutMapping("/admin/about")
    public Result updateAbout(@RequestBody AboutVo aboutVo) {
        websiteConfigService.updateAbout(aboutVo);
        return Result.success();
    }

    /**************************************
     *              博客前台               *
     * ************************************
     */

    /**
     * 查看关于我信息
     * @return
     */
    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result getAbout() {
        String about = websiteConfigService.getAbout();
        return Result.success(about);
    }

}
