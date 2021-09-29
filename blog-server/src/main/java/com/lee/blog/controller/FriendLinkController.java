package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.FriendLinkDTO;
import com.lee.blog.dto.FriendLinkListDTO;
import com.lee.blog.service.FriendLinkService;
import com.lee.blog.vo.*;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.*;

/**
 * <p>
 *  友链控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "友链模块")
@RestController
public class FriendLinkController {

    @Autowired
    FriendLinkService friendLinkService;

    /**************************************
     *              博客后台               *
     **************************************
     */

    /**
     * 根据id删除友链
     * @param linkIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id删除友链")
    @DeleteMapping("/admin/deleteFriendLink")
    public Result deleteFriendLink(@RequestBody List<Long> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return Result.success();
    }

    /**
     * 更新保存友链
     * @param friendLinkVo
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新友链")
    @PostMapping("/admin/saveOrUpdateFriendLink")
    public Result saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVo friendLinkVo) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVo);
        return Result.success();
    }

    /**
     * admin查询友链列表
     * @param conditionVo
     * @return
     */
    @ApiOperation(value = "admin查询友链列表")
    @GetMapping("/admin/links")
    public Result listFriendLinkDTO( ConditionVo conditionVo) {
        PageResult<FriendLinkListDTO> list = friendLinkService.listFriendLinkDTO(conditionVo);
        return Result.success(list);
    }

    /**
     * 修改友链展示状态
     * @param friendLinkStatusVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改友链展示状态")
    @PutMapping("/admin/changeLinkStatus")
    public Result updateLinkStatus(@Valid @RequestBody FriendLinkStatusVo friendLinkStatusVo) {
        friendLinkService.updateLinkStatus(friendLinkStatusVo);
        return Result.success();
    }

    /**************************************
     *              博客前台               *
     **************************************
     */

    /**
     * 前台查询友链列表
     * @return
     */
    @ApiOperation(value = "前台查询友链列表")
    @GetMapping("/links")
    public Result listFriendLinks() {
        List<FriendLinkDTO> list = friendLinkService.listFriendLinks();
        return Result.success(list);
    }

}
