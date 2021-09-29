package com.lee.blog.controller;

import com.lee.blog.dto.BlogAdminInfoDTO;
import com.lee.blog.dto.BlogHomeInfoDTO;
import com.lee.blog.dto.UserAreaDTO;
import com.lee.blog.service.BlogInfoService;
import com.lee.blog.service.UserService;
import com.lee.blog.strategy.UploadStrategyContext;
import com.lee.common.Result.Result;
import com.lee.common.enums.FilePathEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 博客前台后台信息管理模块
 * @author lee
 * @create 2021-09-17 10:43
 **/
@Api(tags = "博客前台后台信息管理模块")
@RestController
public class BlogInfoController {

    @Autowired
    BlogInfoService blogInfoService;

    @Autowired
    UserService userService;

    @Autowired
    UploadStrategyContext uploadStrategyContext;


    /**************************************
     *              后台信息               *
     **************************************
     */

    /**
     * 查询后台信息
     * @return
     */
    @ApiOperation(value = "查询后台信息")
    @GetMapping("/admin")
    public Result getBlogAdminInfo() {
        BlogAdminInfoDTO blogAdminInfoDTO = blogInfoService.getBlogAdminInfo();
        return Result.success(blogAdminInfoDTO);
    }

    /**
     * 获取用户区域分布
     * @param type
     * @return
     */
    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/admin/user/area")
    public Result listUserAreas( Integer type) {
        List<UserAreaDTO> list= userService.listUserAreas(type);
        return Result.success(list);
    }

    @ApiOperation(value = "上传博客配置图片")
    @PostMapping("/admin/config/images")
    public Result savePhotoAlbumCover(MultipartFile file) {
        String photo = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath());
        return Result.success(photo);
    }

    /**************************************
     *              博客前台               *
     **************************************
     */

    /**
     * 上传访客信息
     * @return
     */
    @ApiOperation(value = "上传访客信息")
    @PostMapping("/report")
    public Result report() {
        blogInfoService.report();
        return Result.success();
    }

    /**
     * 查询博客首页信息
     * @return
     */
    @ApiOperation(value = "查询博客首页信息")
    @GetMapping("/")
    public Result getBlogHomeInfo() {
        BlogHomeInfoDTO blogHomeInfo= blogInfoService.getBlogHomeInfo();
        return Result.success(blogHomeInfo);
    }

}
