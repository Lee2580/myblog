package com.lee.blog.controller;


import com.lee.blog.annotation.OptLog;
import com.lee.blog.dto.CommentDTO;
import com.lee.blog.dto.CommentListDTO;
import com.lee.blog.dto.ReplyDTO;
import com.lee.blog.service.CommentService;
import com.lee.blog.vo.CommentVo;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.ReviewVo;
import com.lee.common.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lee.common.comstant.OptTypeConst.REMOVE;
import static com.lee.common.comstant.OptTypeConst.UPDATE;

/**
 * <p>
 *  评论模块控制器
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Api(tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    /*************************************
     *              后台操作              *
     * ***********************************
     */

    /**
     * 审核评论
     * @param reviewVo
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result updateCommentsReview(@Valid @RequestBody ReviewVo reviewVo) {
        commentService.updateCommentsReview(reviewVo);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除评论")
    @DeleteMapping("/admin/deleteComments")
    public Result deleteComments(@RequestBody List<Long> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    /**
     * admin查询评论列表
     * @param condition
     * @return
     */
    @ApiOperation(value = "admin查询评论列表")
    @GetMapping("/admin/listComments")
    public Result getCommentListDTO(ConditionVo condition) {
        PageResult<CommentListDTO> list = commentService.getCommentListDTO(condition);
        return Result.success(list);
    }


    /*************************************
     *              前台操作              *
     * ***********************************
     */

    /**
     * 添加评论
     * @param commentVo
     * @return
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/saveComment")
    public Result saveComment(@Valid @RequestBody CommentVo commentVo) {

        commentService.saveComment(commentVo);
        return Result.success();
    }

    /**
     * 查询某评论下的回复
     * @param commentId
     * @return
     */
    @ApiOperation(value = "查询评论下的回复")
    @GetMapping("/comments/{commentId}/replies")
    public Result listRepliesByCommentId(@PathVariable("commentId") Long commentId) {
        List<ReplyDTO> list= commentService.listRepliesByCommentId(commentId);

        return Result.success(list);
    }

    /**
     * 点赞/取消 评论
     * @param commentId
     * @return
     */
    @ApiOperation(value = "评论点赞")
    @PostMapping("/comments/{commentId}/like")
    public Result saveCommentLike(@PathVariable("commentId") Long commentId) {
        commentService.saveCommentLike(commentId);
        return Result.success();
    }

    /**
     * 查询评论列表
     * @param blogId
     * @return
     */
    @ApiOperation(value = "前台查询评论列表")
    @GetMapping("/comments")
    public Result listComments(Long blogId) {

        PageResult<CommentDTO> list= commentService.listComments(blogId);
        return Result.success(list);
    }

}
