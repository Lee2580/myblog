package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.MessageDTO;
import com.lee.blog.dto.MessageListDTO;
import com.lee.blog.service.MessageService;
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
 *  留言模块控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "留言模块")
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    /**************************************
     *              博客后台               *
     * ************************************
     */

    /**
     * 删除留言
     * @param messageIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除留言")
    @DeleteMapping("/admin/deleteMessages")
    public Result deleteMessages(@RequestBody List<Long> messageIdList) {
        messageService.removeByIds(messageIdList);
        return Result.success();
    }

    /**
     * 审核留言
     * @param reviewVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核留言")
    @PutMapping("/admin/messages/review")
    public Result updateMessagesReview(@Valid @RequestBody ReviewVo reviewVo) {
        messageService.updateMessagesReview(reviewVo);
        return Result.success();
    }

    /**
     * admin查询留言列表
     * @param condition
     * @return
     */
    @ApiOperation(value = "查询留言列表")
    @GetMapping("/admin/listMessages")
    public Result getMessageListDTO(ConditionVo condition) {
        PageResult<MessageListDTO> list= messageService.getMessageListDTO(condition);
        return Result.success(list);
    }

    /**************************************
     *              博客前台               *
     * ************************************
     */

    /**
     * 添加保存留言
     * @param messageVo
     * @return
     */
    @OptLog(optType = SAVE)
    @ApiOperation(value = "添加保存留言")
    @PostMapping("/saveMessage")
    public Result saveMessage(@Valid @RequestBody MessageVo messageVo) {
        messageService.saveMessage(messageVo);
        return Result.success();
    }

    /**
     * 查看留言列表
     * @return
     */
    @ApiOperation(value = "查看留言列表")
    @GetMapping("/listMessages")
    public Result listMessages() {
        List<MessageDTO> list = messageService.listMessages();
        return Result.success(list);
    }

}
